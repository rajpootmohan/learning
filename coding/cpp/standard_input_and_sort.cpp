
#include <stdio.h>
#include <iostream>

using namespace std;

int main() {
	int t; cin>>t;
	while(t>0){
	    int n,s;
	    cin>>n>>s;
	    int a[n];
	    for(int i=0;i<n;i++){
	        cin>>a[i];
	    }
	    int startIndex=0,endIndex=0,sum=0;
	    for(int i=0;i<=n;i++){
	    	// cout<<" i: "<<i<<" sum: "<<sum<<" s: "<<s<<" startIndex: "<<startIndex<<" endIndex: "<<endIndex<<endl;
	        if(sum == s){
	        	cout<<startIndex<<" "<<endIndex<<endl;
	        	break;
	        }else if(sum<s){
	        	sum += a[endIndex];
	        	endIndex++;
	        } else {
	        	sum -= a[startIndex];
	        	startIndex++;
	        }
	    };

	    t--;
	};
	return 0;
}
