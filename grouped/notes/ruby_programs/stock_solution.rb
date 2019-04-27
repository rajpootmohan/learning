def stock_exchange_solution
  input_hash={}
  output_hash=[]
  loop do
    input=gets.chomp
    break if input==""
    input_array = input.split(" ")
    if input_array[3] == "sell"
      input_hash[input_array[2]]||={}
      input_hash[input_array[2]][input_array[5].to_f] ||=[]
      input_hash[input_array[2]][input_array[5].to_f] << [input_array[0], input_array[1].split(":").join.to_i, input_array[4].to_i]
    else
      temp_hash = input_hash[input_array[2]]
      next if temp_hash.nil?
      temp_hash.sort.map do |price,value|
        index_for_deletion = []
        if input_array[5].to_f >= price
          value.each_with_index  do |v1,index|                
            if input_array[4].to_i > v1[2] #qty comparison
              output_hash << [v1[0], v1[2], price,input_array[0]] # #2 80 237.45 #3
              input_array[4] = input_array[4].to_i - v1[2]
              index_for_deletion << index
            else
              output_hash << [v1[0], input_array[4].to_i, price,input_array[0]] # #2 80 237.45 #3
              v1[2] -= input_array[4].to_i
              input_array[4] = 0
              index_for_deletion << index if v1[2] == 0
            end
              break if input_array[4] ==0 
          end
          break if input_array[4] ==0  
        else
          break
        end
        index_for_deletion.each do |i|
          value.delete_at(i)
        end
        temp_hash.delete(price) if value.empty?
      end 
    end
  end
  output_hash.each do |val|
    puts "#{val[0]} #{val[1]} #{val[2]} #{val[3]}"
  end
end    

stock_exchange_solution
