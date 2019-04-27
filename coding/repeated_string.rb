def repeatedString(s, n)
  strlen = s.length;
  counta = s.count('a');
  total_count = (n/strlen)*counta;
  remaining = n%strlen;
  total_count = total_count + s.split("")[0...remaining].count('a');
  return total_count; 
end

a = repeatedString("a",1000000000000)
puts a