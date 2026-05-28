class Node {
    private int data;
    private Node next;

    public Node(int data){
        this.data = data;
        this.next = null;
    }

    public int getData() { return data; }
    public Node getNext() { return next; }
    public void setNext(Node next) { this.next = next; }
}

class LinkedList {
    private Node head;
    private Node tail;

    public LinkedList() {
        head = tail = null;
    }

    public void append(int data) {
        Node temp = new Node(data);

        if (head == null) { // case 0 - no nodes
            head = temp;
        }
        else { // case 1 - 1 or more nodes
            tail.setNext(temp);
        }

        tail = temp;
    }

    public int search(int target) {
        int index = 0;
        Node cur = head;

        while (cur != null) {
            if (cur.getData() == target) {
                return index;
            }
            index++;
            cur = cur.getNext();
        }

        return -1;
    }

    public void print() {
        Node cur = head;

        while (cur != null) {
            System.out.print(cur.getData() + ", ");

            cur = cur.getNext();
        }
    }
}

class LinkedListTest {
    public static void Main(String[] args) {
        LinkedList linkedList = new LinkedList();

        linkedList.append(1);
        linkedList.append(2);   
        linkedList.append(3);
        
        System.out.println(linkedList.search(0));
        System.out.println(linkedList.search(2));
        System.out.println(linkedList.search(3));

        linkedList.print();
    }
}