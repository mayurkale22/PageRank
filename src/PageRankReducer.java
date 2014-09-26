import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer 
	extends Reducer<Text, Text, Text, Text>{
	@Override
	/**
	 *  reduce method accepts the Key Value pairs from mappers, 
	 *  do the aggregation based on keys and produce the final out put.
	 *  @param  key, values, context
	 **/
	public void reduce(Text key, Iterable<Text> values, Context context) 
		throws IOException, InterruptedException{
		
		String outlinks = "";
		String inputValue = "";
		float PR = 0;
		String specialChar = ",";
		
        /** 
         * iterates through all the values available with a key and add them together and give the
         * final result as the key and sum of its values
         **/
		for (Text value : values) {
			inputValue = value.toString();
			
			// If input value contains ",", split it and calculate new PR. 
			if (inputValue.contains(specialChar)) {
				PR += Float.parseFloat(inputValue.split(specialChar)[1]);
			} else {
				outlinks = inputValue;
			}
		}
		context.write(key, new Text(outlinks + PR));
	}
}