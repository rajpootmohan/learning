class Node
  attr_accessor :data, :next

  def initialize(data)
    @data = data;
    @next = nil;
  end

  def to_s
    "Node with value #{@data}";
  end
end

class LinkedList
  attr_accessor :head
  
  def initialize
    @head = nil;
  end

  def append_value(data)
   if @head
     final_tail.next = Node.new(data)
   else 
     @head = Node.new(data)
   end 
  end

  def final_tail 
    node = @head;
    return node if !node.next
    return node if !node.next while (node = node.next)
  end 

  def print
    node = @head
    puts node
    while (node = node.next)
      puts node
    end
  end
   
end

def reversePrint(node)
  return nil if !node
  reversePrint(node.next)
  puts node.data 
end

l = LinkedList.new
l.append_value(10);
l.append_value(30);
l.print
reversePrint(l.head);
