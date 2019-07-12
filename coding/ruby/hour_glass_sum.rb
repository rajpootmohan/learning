def hourglassSum(arr)
  finalsum = -9999999999;
  for i in 1..4 do
      for j in 1..4 do
          localsum = arr[i][j] + arr[i-1][j-1] + arr[i-1][j] + arr[i-1][j+1] +
            arr[i+1][j-1] + arr[i+1][j] + arr[i+1][j+1];
          if finalsum < localsum
            finalsum = localsum
          end     
      end
  end
  return finalsum
end

a = [[-9, -9, -9,  1, 1, 1],
[ 0, -9,  0,  4, 3, 2],
[-9, -9, -9,  1, 2, 3],
[ 0,  0,  8,  6, 6, 0],
[ 0,  0,  0, -2, 0, 0],
[ 0,  0,  1,  2, 4, 0]];

sum = hourglassSum(a)
puts sum;