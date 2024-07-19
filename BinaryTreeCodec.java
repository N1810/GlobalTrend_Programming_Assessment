class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class BinaryTreeCodec {

    private static final String MARKER = "X";

    public String serialize(TreeNode root) {
        if (root == null) {
            return MARKER;
        }
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }

    private void serializeHelper(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(MARKER).append(",");
            return;
        }
        sb.append(root.val).append(",");
        serializeHelper(root.left, sb);
        serializeHelper(root.right, sb);
    }

    public TreeNode deserialize(String data) {
        String[] values = data.split(",");
        return deserializeHelper(values);
    }

    private TreeNode deserializeHelper(String[] values) {
        if (values[0].equals(MARKER)) {
            values[0] = values[1]; 
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        values[0] = values[1]; 
        root.left = deserializeHelper(values);
        root.right = deserializeHelper(values);
        return root;
    }
}
