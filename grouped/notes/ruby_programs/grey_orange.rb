$process_hash = {}
$item_id = 0
$default_arm_id = 5
$default_code = 200

# $process_hash contains structure like 
# {item_id => {arm_id => 121, weight => 23.4, barcode => '1243434', code => 201}}
# default arm_id is 5, if any item is stuck in any place

class Item

	#200 initiated
	#201 scan done
	#202 weight done
	#203 sorting done
	#401 scan incomplete
	#402 weight incomplete
	#403 sort incomplete

	def scan_me
		# put an entry in $process_hash with some incremented id and default code that moves to default bucket
    	$item_id++;
		$process_hash[$item_id] = {"arm_id" => $default_arm_id, "weight" => nil, "barcode" => nil, "code" => $default_code}
		# send request for barcode scanning with $item_id
			# update barcode in $process_hash of $item_id			
			$process_hash.each do |key, value|
				if response.key == key
					value["barcode"] = response.barcode
				end
			end            
		# send request to DB for fetching data using barcode        
		   # get response with $item_id
           # update the corresponding $item_id wich code
			$process_hash.each do |key, value|
				if response.key == key
					value["arm_id"] = response.arm_id
				end
			end            
	end

	def weight_me weight
	# weight the current item and update in $process_hash
		$process_hash.each do |key, value|
			if value["code"] == 201
				# send request to DB for fetching data using barcode and weight and item_id
	 		    # get response with $item_id
		        # update the corresponding $item_id wich code
				$process_hash.each do |key, value|
					if response.key == key
						if success	
							value["weight"] = weight
							value["code"] = 202
						else
							value["code"] = 402
						end
					end
				end				
			end
			break;
		end
	end

	def sort_me
		# send arm_id of first item from $process_hash	
		# remove from hash
		arm_id = $process_hash.first["arm_id"]
		$process_hash.first.destroy
		return arm_id
	end
end 



