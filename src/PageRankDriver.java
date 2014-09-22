import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

// This driver class is responsible for triggering the map reduce job in Hadoop.

public class PageRankDriver {
	public static void main(String[] args) 
		throws IOException, ClassNotFoundException, InterruptedException {
		
		// Check for valid number of arguments.
		if (args.length != 2) {
			System.err.println("*** Error: Missing Parameters *** \n " +
							   "Usage: hadoop PageRankDriver <input_path> <output_path>");
			System.exit(-1);
		}
		
		/**
		 * Create a new job object and set the output types of the Map and Reduce function.
		 * Also set Mapper and Reducer classes.
		 */
		Job job = new Job();
		job.setJobName("Page Rank");
		job.setJarByClass(PageRankDriver.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		job.setMapperClass(PageRankMapper.class);
		job.setReducerClass(PageRankReducer.class);
		
		// the HDFS input and output directory to be fetched from the command line
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

