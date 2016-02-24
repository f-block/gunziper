/**
 * Erstellt am: Jul 18, 2014 7:22:38 PM
 * Erstellt von: surf
 * Projekt: gunziper_branch
 */

package docu;

import java.awt.GridLayout;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import misc.Messages;


public class HelpPanel extends JPanel implements TreeSelectionListener {


    private class BookInfo {

        public String bookName;
        public URL    bookURL;

        public BookInfo(String book, String filename) {

            this.bookName = book;
            this.bookURL = this.getClass().getResource(filename);
        }

        @Override
        public String toString() {

            return this.bookName;
        }
    }

    private JEditorPane            helpPane = null;
    private DefaultMutableTreeNode rootNode = null;
    private JTree                  tree     = null;
    private URL                    startUrl = null;

    public HelpPanel() {

        super(new GridLayout(1, 0));

        this.rootNode = new DefaultMutableTreeNode(Messages
                .getString("gunziper.documentation"));
        this.createNodes(this.rootNode);
        this.startUrl = this.getClass().getResource("index.html");
        this.tree = new JTree(this.rootNode);
        this.tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.tree.addTreeSelectionListener(this);
        this.tree.setExpandsSelectedPaths(true);

        JScrollPane treeView = new JScrollPane(this.tree,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.helpPane = new JEditorPane();
        this.helpPane.setEditorKit(JEditorPane
                .createEditorKitForContentType("text/html"));
        this.helpPane.setEditable(false);
        this.helpPane.addHyperlinkListener(new HyperlinkListener() {

            public void hyperlinkUpdate(HyperlinkEvent e) {

                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    HelpPanel.this.displayURL(e.getURL());
                    HelpPanel.this.setSelectedNode(e.getURL());
                }
            }
        });


        JScrollPane htmlView = new JScrollPane(this.helpPane,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(treeView);
        splitPane.setRightComponent(htmlView);
        splitPane.setResizeWeight(0.1);


        this.add(splitPane);

        this.displayURL(this.startUrl);
    }


    public void createNodes(DefaultMutableTreeNode top) {

        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("request.unpacking"), "reqUnpacking.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("response.unpacking"), "respUnpacking.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("match.replace"), "matchReplace.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("regex.text"), "regexTest.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("deserialization"), "deserialization.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("request.packing.for.intruder"),
                "reqUnpackingIntruder.html")));
        top.add(new DefaultMutableTreeNode(new BookInfo(Messages
                .getString("intruder.comparer"), "intruderComparer.html")));
    }

    private void displayURL(URL url) {

        try {
            if (url != null) {
                this.helpPane.setPage(url);
            }
            else {
                this.helpPane
                        .setText("<h1>"
                                + Messages
                                        .getString("an.error.occured.the.documentation.is.not.available.this.shouldn't.happen.just.contact.the.author.of.the.plugin.and.ask.him.what.the.fu...he.has.done.this.time")
                                + "</h1>");

            }
        }catch (IOException e) {
            this.helpPane
                    .setText("<h1>"
                            + Messages
                                    .getString("an.error.occured.the.documentation.is.not.available.this.shouldn't.happen.just.contact.the.author.of.the.plugin.and.ask.him.what.the.fu...he.has.done.this.time")
                            + "</h1>");
        }
    }


    public void setSelectedNode(URL link) {

        DefaultMutableTreeNode temp = this.rootNode;
        while ((temp = temp.getNextNode()) != null) {
            if (((BookInfo) temp.getUserObject()).bookURL.equals(link)) {
                this.tree.setSelectionPath(new TreePath(temp.getPath()));
                return;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event
     * .TreeSelectionEvent)
     */
    @Override
    public void valueChanged(TreeSelectionEvent arg0) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.tree
                .getLastSelectedPathComponent();

        if (node == null)
            return;

        Object nodeInfo = node.getUserObject();
        if (node.isLeaf()) {
            BookInfo book = (BookInfo) nodeInfo;
            this.displayURL(book.bookURL);

        }
        else {
            this.displayURL(this.startUrl);
        }
    }
}
