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
		if (args.length != 3) {
			System.err.println("*** Error: Missing Parameters *** \n " +
							   "Usage: hadoop PageRankDriver <indir> <outdir> <iterations>");
			System.exit(-1);
		}
		
		Path inPath = new Path(args[0]);
		Path outPath =  null;
		int iterations = new Integer(args[2]);
		
		for (int i = 0; i<iterations; ++i){
		    outPath = new Path(args[1]+i);
		    
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
			FileInputFormat.addInputPath(job, inPath);
			FileOutputFormat.setOutputPath(job, outPath);
			job.waitForCompletion(true);
			inPath = outPath;
		}
	}
}

