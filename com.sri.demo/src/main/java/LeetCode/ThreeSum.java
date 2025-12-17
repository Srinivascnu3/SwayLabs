package LeetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThreeSum {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a []= {1,-10,5,5,8,4,6};
		int target =18;
		int sum =0;
		Arrays.sort(a);

		for (int i = 0; i < a.length; i++) {
			int j=i+1;
			int k=a.length-1;
			while(j<k) {
				int tar=a[j]+a[k];
				if(a[i]==target-tar) {
					System.out.println(a[i]+" "+a[j]+" "+a[k]);
					j++;
					k--;
				}
				else if (a[i]<(target-tar)){
					j++;
				}
				else if(a[i]>(target-tar)){
					k--;
				}
			}
		}
	
	
	}

}

/**
int a []= {1,-10,5,5,8,4,6};
int target =18;
int sum =0;
Arrays.sort(a);
Map<Integer,Integer> m = new HashMap<>();
for (int i = 0; i < a.length; i++) {
	m.put(a[i],i );
}
for (int i = 0,j=a.length-1; i < a.length; i++) {
	sum=a[i]+a[j];
	if (m.containsKey(target-sum)) {
		System.err.println(target-sum+" "+a[i]+" "+a[j]);
		break;
	}
	else if (sum>target-sum) {
		j--;
	}
	
}
 */

