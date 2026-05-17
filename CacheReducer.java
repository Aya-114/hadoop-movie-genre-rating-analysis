import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CacheReducer extends Reducer<Text, IntWritable, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int ratingCount = 0;
        int ratingSum = 0;
        int fiveStarCount = 0;

        for (IntWritable value : values) {
            int rating = value.get();

            ratingCount++;
            ratingSum += rating;

            if (rating == 5) {
                fiveStarCount++;
            }
        }

        double avgRating = 0.0;

        if (ratingCount > 0) {
            avgRating = (double) ratingSum / ratingCount;
        }

        String result = ratingCount + "\t"
                + String.format("%.2f", avgRating) + "\t"
                + fiveStarCount;

        context.write(key, new Text(result));
    }
}