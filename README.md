# 🎬 Movie Genre Rating Analysis

A Hadoop MapReduce analytics project that processes large-scale movie ratings and metadata using DistributedCache and HashMap lookups. The system analyzes movie genres, calculates rating statistics, validates malformed records, and supports scalable distributed processing for datasets larger than 1GB.

---

# 🚀 Project Overview

This project analyzes movie ratings grouped by genre using Hadoop MapReduce.

The system loads movie metadata into memory using Hadoop DistributedCache, then joins it with large-scale rating datasets during the mapping phase.

The project calculates:

* Total ratings per genre
* Average rating per genre
* Number of five-star ratings
* Invalid rating detection and logging

The solution is optimized for scalable distributed processing and large datasets.

---

# ✨ Features

✅ Hadoop DistributedCache integration
✅ Genre lookup using HashMap
✅ Average rating calculation
✅ Five-star rating analysis
✅ Invalid record handling & logging
✅ Hadoop counters support
✅ Large dataset processing (1GB+)
✅ Optimized reducer aggregation
✅ Sample input/output included

---

# 📂 Input Files

## movies.txt

movieId, title, genre, releaseYear

Example:
M001, The Grand Illusion, Drama, 1937

---

## ratings_1gb.txt

ratingId, movieId, userId, rating

Example:
RT01, M001, U10, 4

---

# 📤 Output Example

Comedy    3    4.00    1
Drama     5    4.00    2

Format:

genre | ratingCount | avgRating | fiveStarCount

---

# 🧠 Hadoop Components

## CacheMapper.java

* Loads movie metadata into HashMap during setup()
* Extracts genre using movieId lookup
* Emits:
  (genre, rating)
* Emits UNKNOWN for missing movieIds
* Rejects invalid ratings outside 1–5

---

## CacheReducer.java

* Aggregates ratings for each genre
* Calculates:

  * ratingCount
  * averageRating
  * fiveStarCount
* Formats average rating using:
  String.format("%.2f", ...)

---

## CacheDriver.java

* Configures MapReduce job
* Registers DistributedCache file
* Sets reducers to 1
* Defines input/output paths

---

# 🛠️ Technologies Used

| Category        | Tools                      |
| --------------- | -------------------------- |
| Language        | Java                       |
| Framework       | Hadoop MapReduce           |
| Data Processing | DistributedCache           |
| Storage         | HDFS                       |
| Logging         | Hadoop Counters & LOG.warn |

---

# 📁 Project Structure

movie-genre-rating-analysis/
│
├── code/
│   ├── CacheDriver.java
│   ├── CacheMapper.java
│   └── CacheReducer.java
│
├── screenshots/
│
├── movies.txt
├── ratings_1gb.txt
├── sample_input.txt
├── sample_output.txt
├── assumptions.txt
├── README.txt
└── task5.jar

---

# ⚡ How to Run

## Compile

javac -classpath `hadoop classpath` *.java

## Create JAR

jar cf task5.jar *.class

## Run Hadoop Job

hadoop jar task5.jar CacheDriver movies.txt input output

---

# ✅ Validation & Error Handling

The project handles:

* Malformed records
* Missing fields
* Invalid ratings
* Unknown movie IDs
* Non-numeric values

Invalid records are skipped and logged using Hadoop counters and LOG.warn.

---

# 📊 Expected Results

The project generates:

* Genre-based rating statistics
* Average ratings
* Five-star distributions
* Scalable distributed analytics results

---

# 👩‍💻 Developed By

Aya Alaa
Asmaa Mohamed 
Doha Mohamed
Ahmed Aymen
Marina Ashraf
Sara Mohamed
