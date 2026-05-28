class Node {
    private int val;
    private Node link;

    public Node(int val)
    {
        this.val = val;
        this.link = null;
    }

    public void setVal(int val)
    {
        this.val = val;
    }
    public void setLink(Node link)
    {
        this.link = link;
    }

    public int getVal()
    {
        return val;
    }
    public Node getLink()
    {
        return link;
    }
}

class LinkedList
{
    private Node head;
    private Node tail;
    private int size;

    public LinkedList()
    {
        head = tail = null;
    }

    // add item
    public void add_item(int val)
    {
        Node temp =  new Node(val);

        if (head == null)
        {
            head = temp;
        }
        else
        {
            tail.setLink(temp);
        }


        tail = temp;
        size++;
    }

    // search for item
    public int find_item(int val)
    {
        int index = 0;
        Node temp = head;

        while (temp.getLink() != null)
        {
            if (temp.getVal() == val)
            {
                return index;
            }
            index++;
        }

        return -1;
    }

    // print list
    public void print_list()
    {
        Node temp = head;

        while (temp.getLink() != null)
        {
            System.out.print(temp.getVal() + ", ");
            temp = temp.getLink(); 
        }
    }

    // delete item
    public void delete_item(int val)
    {
        Node temp = head;

        while (temp.getLink() != null)
        {
            if (temp.getVal() == head.getVal())
            {
                head = head.getLink();
            }
            
            else if (temp.getLink().getVal() == val)
            {
                temp.setLink(temp.getLink().getLink());
            }

            size--;
        }
    }

    // get size
    public int get_size()
    {
        return this.size;
    }
}


class LinkedListTester
{
    public static void main(String args[])
    {
        LinkedList list = new LinkedList();

        list.add_item(0);
        list.add_item(1);
        list.add_item(2);
        System.out.println(list.get_size());
        
        list.print_list();
        System.out.println(list.find_item(1));

        list.delete_item(1);
        System.out.println(list.get_size());
        list.print_list();
    }
}