import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CacheMapper extends Mapper<Object, Text, Text, IntWritable> {

    private static final Log LOG = LogFactory.getLog(CacheMapper.class);

    private HashMap<String, String> movieGenreMap = new HashMap<String, String>();
    private Text outKey = new Text();
    private IntWritable outValue = new IntWritable();

    public enum Task5Counters {
        PROCESSED_LINES,
        SKIPPED_LINES,
        INVALID_RATING,
        MALFORMED_LINES,
        UNRECOGNIZED_MOVIE_IDS
    }

    @Override
    protected void setup(Context context) throws IOException {
        URI[] cacheFiles = context.getCacheFiles();

        if (cacheFiles == null || cacheFiles.length == 0) {
            LOG.warn("No cache file found.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader("movies.txt"));
        String line;

        while ((line = br.readLine()) != null) {
            String[] fields = line.split(",");

            if (fields.length >= 3) {
                String movieId = fields[0].trim();
                String genre = fields[2].trim();

                if (!movieId.isEmpty() && !genre.isEmpty()) {
                    movieGenreMap.put(movieId, genre);
                }
            }
        }

        br.close();
    }

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {

        String line = value.toString();
        String[] fields = line.split(",");

        if (fields.length < 4) {
            LOG.warn("Malformed line skipped: " + line);
            context.getCounter(Task5Counters.MALFORMED_LINES).increment(1);
            context.getCounter(Task5Counters.SKIPPED_LINES).increment(1);
            return;
        }

        String movieId = fields[1].trim();
        String ratingText = fields[3].trim();

        try {
            int rating = Integer.parseInt(ratingText);

            if (rating < 1 || rating > 5) {
                LOG.warn("Invalid rating skipped: " + line);
                context.getCounter(Task5Counters.INVALID_RATING).increment(1);
                context.getCounter(Task5Counters.SKIPPED_LINES).increment(1);
                return;
            }

            String genre = movieGenreMap.get(movieId);

            if (genre == null) {
                genre = "UNKNOWN";
                context.getCounter(Task5Counters.UNRECOGNIZED_MOVIE_IDS).increment(1);
            }

            outKey.set(genre);
            outValue.set(rating);

            context.write(outKey, outValue);
            context.getCounter(Task5Counters.PROCESSED_LINES).increment(1);

        } catch (NumberFormatException e) {
            LOG.warn("Unparseable rating skipped: " + line);
            context.getCounter(Task5Counters.MALFORMED_LINES).increment(1);
            context.getCounter(Task5Counters.SKIPPED_LINES).increment(1);
        }
    }

    @Override
    protected void cleanup(Context context) {
        LOG.info("Mapper cleanup finished. Check Hadoop counters for processed and skipped lines.");
    }
}