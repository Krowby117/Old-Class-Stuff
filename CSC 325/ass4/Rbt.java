class RbtNode 
{
    private int data;
    private byte color;
    private RbtNode left, right, parent;

    public static final byte CL_RED = 0;
    public static final byte CL_BLACK = 1;

    // node constructor
    public RbtNode(int data) {
        this.data = data;
        this.color = CL_RED;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    // getter methods
    public int getData() { 
        return data; 
    }
    public byte getColor() { 
        return color; 
    }
    public RbtNode getLeft() { 
        return left; 
    }
    public RbtNode getRight() { 
        return right; 
    }
    public RbtNode getParent() { 
        return parent; 
    }

    // setter methods
    public void setData(int data) { 
        this.data = data; 
    }
    public void setColor(byte color) { 
        this.color = color; 
    }
    public void setLeft(RbtNode left) { 
        this.left = left; 
    }
    public void setRight(RbtNode right) { 
        this.right = right; 
    }
    public void setParent(RbtNode parent) { 
        this.parent = parent; 
    }

    // other methods
    public RbtNode getGrandParent() 
    {
        if (parent != null) return parent.getParent();
        return null;
    }

    public RbtNode getUncle() 
    {
        RbtNode grandParent = getGrandParent();
        if (grandParent == null) return null;
        if (parent == grandParent.getLeft()) return grandParent.getRight();
        return grandParent.getLeft();
    }
}

class Rbt 
{
    public RbtNode root;

    public Rbt() 
    {
        root = null;
    }

    public void insert(int val) 
    {
        RbtNode hold = new RbtNode(val);
        root = bstInsert(root, hold);
        issueFixer(hold);
    }

    private RbtNode bstInsert(RbtNode root, RbtNode hold) {
        if (root == null) return hold;

        if (hold.getData() < root.getData()) {
            root.setLeft(bstInsert(root.getLeft(), hold));
            root.getLeft().setParent(root);
        } else if (hold.getData() > root.getData()) {
            root.setRight(bstInsert(root.getRight(), hold));
            root.getRight().setParent(root);
        }

        return root;
    }

    private void issueFixer(RbtNode node) {
        RbtNode parent = null;
        RbtNode grandParent = null;

        while (node != root && node.getColor() == RbtNode.CL_RED && node.getParent().getColor() == RbtNode.CL_RED) 
        {
            parent = node.getParent();
            grandParent = node.getGrandParent();

            if (parent == grandParent.getLeft()) 
            {
                RbtNode uncle = grandParent.getRight();

                if (uncle != null && uncle.getColor() == RbtNode.CL_RED) 
                {
                    grandParent.setColor(RbtNode.CL_RED);
                    parent.setColor(RbtNode.CL_BLACK);
                    uncle.setColor(RbtNode.CL_BLACK);
                    node = grandParent;
                } else 
                {
                    if (node == parent.getRight()) 
                    {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.getParent();
                    }

                    rotateRight(grandParent);
                    byte tmp = parent.getColor();
                    parent.setColor(grandParent.getColor());
                    grandParent.setColor(tmp);
                    node = parent;
                }
            } else 
            {
                RbtNode uncle = grandParent.getLeft();

                if (uncle != null && uncle.getColor() == RbtNode.CL_RED) 
                {
                    grandParent.setColor(RbtNode.CL_RED);
                    parent.setColor(RbtNode.CL_BLACK);
                    uncle.setColor(RbtNode.CL_BLACK);
                    node = grandParent;
                } else 
                {
                    if (node == parent.getLeft()) 
                    {
                        rotateRight(parent);
                        node = parent;
                        parent = node.getParent();
                    }

                    rotateLeft(grandParent);
                    byte tmp = parent.getColor();
                    parent.setColor(grandParent.getColor());
                    grandParent.setColor(tmp);
                    node = parent;
                }
            }
        }
        root.setColor(RbtNode.CL_BLACK);
    }

    private void rotateLeft(RbtNode node) 
    {
        RbtNode rightNode = node.getRight();
        node.setRight(rightNode.getLeft());

        if (node.getRight() != null) 
        {
            node.getRight().setParent(node);
        }

        rightNode.setParent(node.getParent());

        if (node.getParent() == null) 
        {
            root = rightNode;
        }
        else if (node == node.getParent().getLeft()) 
        {
            node.getParent().setLeft(rightNode);
        } else 
        { 
            node.getParent().setRight(rightNode);
        }

        rightNode.setLeft(node);
        node.setParent(rightNode);
    }

    private void rotateRight(RbtNode node) 
    {
        RbtNode leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());

        if (node.getLeft() != null)
        {
            node.getLeft().setParent(node);
        }

        leftNode.setParent(node.getParent());

        if (node.getParent() == null)
        {
            root = leftNode;
        }
        else if (node == node.getParent().getLeft())
        {
            node.getParent().setLeft(leftNode);
        } else {
            node.getParent().setRight(leftNode);

        }
        
        leftNode.setRight(node);
        node.setParent(leftNode);
    }

    public boolean search(int value) {
        return recurSearch(root, value);
    }

    private boolean recurSearch(RbtNode root, int value) 
    {
        if (root == null)
        {
            return false;
        }
        if (root.getData() == value)
        {
            return true;
        }
        if (value < root.getData())
        {
            return recurSearch(root.getLeft(), value);
        }

        return recurSearch(root.getRight(), value);
    }

    public int min() 
    {
        if (root == null)
        {
            return -1;
        }

        RbtNode current = root;

        while (current.getLeft() != null)
        {
            current = current.getLeft();
        }

        return current.getData();
    }

    public int max() 
    {
        if (root == null)
        {
            return -1;
        }

        RbtNode current = root;

        while (current.getRight() != null)
        {
            current = current.getRight();
        }

        return current.getData();
    }

    public int size() {
        return recurSize(root);
    }

    private int recurSize(RbtNode node) {
        if (node == null)
        {
            return 0;
        }

        return 1 + recurSize(node.getLeft()) + recurSize(node.getRight());
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        recursOrder(root, sb);
        return sb.toString();
    }

    private void recursOrder(RbtNode node, StringBuilder sb) 
    {
        if (node == null)
        {
            return;
        }

        recursOrder(node.getLeft(), sb);
        sb.append(node.getData()).append(" ");
        recursOrder(node.getRight(), sb);
    }
}