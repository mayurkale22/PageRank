import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

public class PageRankMapper
	extends Mapper<LongWritable, Text, Text, Text> {

	@Override
	/**
	  * map method that performs the tokenizer job and framing the initial key value pairs
	  * @param  key  is a long integer offset.
	  * @param  value is a line of text.
	  * @param  context is an instance of Context to write output to.
	  **/
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException{
		
		// value is space separated values: A C F PR
		String line = value.toString();
        String[] token_arr = line.split("\t");
        int tokenSize = token_arr.length - 1;
        
		StringBuffer outLinks = new StringBuffer();
		String strTab = "\t";
		Float PR;
		
        for (int index = 1; index < tokenSize; index++){
        	PR = (Float.parseFloat(token_arr[tokenSize]) / (tokenSize - 1));
			context.write(new Text(token_arr[index]), new Text(token_arr[0] + "," + PR.toString()));
			outLinks.append(token_arr[index] + strTab); 
        }
        context.write(new Text(token_arr[0]), new Text(outLinks.toString()));
	}
}
