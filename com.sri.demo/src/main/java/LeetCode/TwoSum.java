package LeetCode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a[]= {2,1,-5,4,9,-3,7};
		int target =6;
		
		Map<Integer,Integer> m = new HashMap<>();
		int sum=0;
		for (int i = 0; i < a.length; i++) {
			sum=target-a[i];
			if (m.containsKey(sum)) {
				System.out.println(m.get(sum)+" "+i); 
			}
			else {
				m.put(a[i], i);
			}
			
		}
		
	}

}
