def countingValleys(n, s)
    valleys = 0;
    level = 0;
    s.scan(/\w/).each do |i|
      if i == 'D'
        level = level - 1;
      else 
        if level == -1
          valleys = valleys + 1;
        end
        level = level + 1;
      end    
    end
    return valleys;
end

a = countingValleys 12, "DDUUDDUDUUUD"
puts a