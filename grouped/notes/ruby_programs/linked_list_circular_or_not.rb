require "/home/mohan/Dropbox/Interviews_Questions/linked_list_implementation_in_ruby"

def is_circular_linked_list? current
  if (current.next_node == nil || (current.next_node).next_node == nil)
    return false
  else
    slow_pointer = current
    fast_pointer = current.next_node.next_node
    while(fast_pointer != nil && fast_pointer.next_node != nil && fast_pointer.next_node.next_node != nil)
      if slow_pointer.eql? fast_pointer
        return true
      end
      slow_pointer = slow_pointer.next_node
      fast_pointer = fast_pointer.next_node.next_node
    end
    return false
  end
end

ll = LinkedList.new(5)
ll.add_value 10
ll.add_value 20
ll.add_value 30
ll.display
ll.remove_value 30
ll.display
value = is_circular_linked_list? ll.head
puts value
