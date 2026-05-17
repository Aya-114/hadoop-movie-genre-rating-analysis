Project Title:
Movie Genre Rating Analysis using Hadoop MapReduce

Project Description:
This project analyzes movie ratings grouped by movie genre using Hadoop MapReduce and Distributed Cache.

Dataset Description:

1. movies.txt
Format:
movieId,title,genre,releaseYear

Example:
M001,The Grand Illusion,Drama,1937

2. ratings_1gb.txt
Format:
ratingId,movieId,userId,rating

Example:
RT01,M001,U10,4

Output Format:
genre    ratingCount    avgRating    fiveStarCount

Example Output:
Drama    2    4.50    1
Action   1    3.00    0

Implemented Components:
1. CacheMapper.java
   - Loads movies.txt into HashMap using Distributed Cache.
   - Validates malformed lines and invalid ratings.
   - Emits (genre, rating).

2. CacheReducer.java
   - Calculates:
     * ratingCount
     * avgRating
     * fiveStarCount

3. CacheDriver.java
   - Configures and runs the Hadoop MapReduce job.
   - Registers Distributed Cache.
   - Sets reducer count to 1.

Features:
- Distributed Cache
- HashMap lookup
- Hadoop Counters
- Data Validation
- Large File Handling (1GB)
- Error Handling
- UNKNOWN genre handling

Validation Rules:
- Missing fields are skipped.
- Malformed lines are skipped.
- Non-numeric ratings are skipped.
- Ratings outside range 1-5 are rejected.

Execution Command:
hadoop jar task5.jar CacheDriver \
/user/hadoop/Project_BigData_task5/ratings_1gb.txt \
/user/hadoop/Project_BigData_task5/output_task5_no_combiner \
/user/hadoop/Project_BigData_task5/movies.txt

Sample Output:
Action    55690    3.12    5429
Action|Adventure    177812    3.83    55469
