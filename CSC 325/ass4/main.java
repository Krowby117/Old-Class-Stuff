public class main {
    public static void main(String[] args) {
        // initialize the Rbt object
        Rbt tree = new Rbt();

        // load up the tree with some values
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);

        // test out the tree
        while (true) {
            Vis.showTree(tree);
            if (!Vis.showMenu(tree)) {
                break;
            }
        }
    }
}