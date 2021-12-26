import java.util.ArrayList;
import java.util.List;

/**
 * Binary tree printer
 * @see <a href="https://stackoverflow.com/questions/8964279/coding-a-basic-pretty-printer-for-trees-in-java">Stack Overflow</a>
 * @author saiteja
 * @author Denis Afanasev
 */
public class TreePrinter {
    /** Node that can be printed */
    public interface PrintableNode {
        /** Get left child */
        PrintableNode getLeft();

        /** Get right child */
        PrintableNode getRight();

        /** Get text to be printed */
        String getText();
    }

    /**
     * Print a tree
     *
     * @param root tree root node
     */
    public static String print(PrintableNode root) {
        StringBuilder builder = new StringBuilder();
        List<List<String>> lines = new ArrayList<>();

        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getText();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (line.get(j) != null) c = '└';
                        }
                    }
                    builder.append(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        builder.append(" ".repeat(Math.max(0, perpiece - 1)));
                    } else {

                        builder.append((j % 2 == 0 ? " " : "─").repeat(Math.max(0, hpw)));
                        builder.append(j % 2 == 0 ? "┌" : "┐");
                        builder.append((j % 2 == 0 ? "─" : " ").repeat(Math.max(0, hpw)));
                    }
                }
                builder.append("\n");
            }

            // print line of numbers
            for (String f : line) {
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                builder.append(" ".repeat(Math.max(0, gap1)));
                builder.append(f);
                builder.append(" ".repeat(Math.max(0, gap2)));
            }
            builder.append("\n");

            perpiece /= 2;
        }
        return builder.toString();
    }
}
