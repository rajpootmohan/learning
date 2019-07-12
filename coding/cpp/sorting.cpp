
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
	    sort(a, a+n);
	    for(int i=0;i<n;i++){
	        cout<<a[i]<<" ";
	    }

	    t--;
	};
	return 0;
}
