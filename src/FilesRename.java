import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FilesRename {
    /**
     * 初始化屏幕显示宽
     */
    public static int viewWidth = getDisplayMode().getWidth() / 2;
    /**
     * 初始化屏幕显示高
     */
    public static int viewHeight = getDisplayMode().getHeight() / 2;
    public static String filePath = "";

    public static void reNames(JTextField inputFilesNewNameText, JTextArea jTextArea) {
        int count = 1;
        jTextArea.setText("");
        File[] oldFiles = new File(filePath).listFiles();
        for (File f : oldFiles) {
            String oldFileName = filePath + "\\\\" + f.getName();
            String fileTyle = f.getName().substring(f.getName().lastIndexOf("."), f.getName().length());
            File oldFile = new File(oldFileName);
            String newFileName = filePath + "\\\\" + inputFilesNewNameText.getText() + "第" + count + "页" + fileTyle;
            File newFile = new File(newFileName);
            if (oldFile.exists() && oldFile.isFile()) {
                oldFile.renameTo(newFile);
                count++;
                jTextArea.append(f.getName() + "\t>>>>>>\t" + newFile.getName() + "\r\n");
            }
        }
    }

    public static JButton reNamesConfirmButton(JPanel mainPanel, final JTextField inputFilesNewNameText, final JTextArea resultView) {
        JButton reNamesConfirm = new JButton("重命名全部文件");
        reNamesConfirm.setVisible(true);
        reNamesConfirm.setBounds(viewWidth - (viewWidth / 4), viewHeight / 6, viewWidth / 6, 30);
        reNamesConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reNames(inputFilesNewNameText, resultView);
            }
        });
        mainPanel.add(reNamesConfirm);
        return reNamesConfirm;
    }

    public static JTextArea resultViewDataBarea(JPanel mainPanel) {
        JTextArea resultViewDataBarea = new JTextArea();
        resultViewDataBarea.setVisible(true);
        resultViewDataBarea.setBounds(viewWidth / 6, viewHeight / 4, viewWidth / 2, viewHeight / 2);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVisible(true);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(viewWidth / 6, viewHeight / 4, viewWidth / 2, viewHeight / 2);
        scrollPane.setViewportView(resultViewDataBarea);

        mainPanel.add(scrollPane);

        return resultViewDataBarea;
    }

    public static JTextField inputFilesNewNameTextField(JPanel mainPanel) {
        JTextField inputFilesNewNameText = new JTextField();
        inputFilesNewNameText.setVisible(true);
        inputFilesNewNameText.setBounds(viewWidth / 6, viewHeight / 6, viewWidth / 2, 30);
        mainPanel.add(inputFilesNewNameText);
        return inputFilesNewNameText;
    }

    public static JLabel viewInputRename(JPanel mainPanel) {
        JLabel jLabel = new JLabel("文件名更改为：");
        jLabel.setVisible(true);
        jLabel.setBounds(viewWidth / 24, viewHeight / 6, 100, 30);
        mainPanel.add(jLabel);
        return jLabel;
    }

    public static void selectFileDir(JTextField jTextField, JTextArea jTextArea) {
        jTextArea.setText("");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fileChooser.showOpenDialog(fileChooser);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            //这个就是你选择的文件夹的路径
            filePath = fileChooser.getSelectedFile().getAbsolutePath();
            jTextField.setText(filePath);
            if (filePath.contains("\\")) {
                filePath = filePath.replace("\\", "\\\\");
            }
            if (filePath != null & filePath != "") {
                findFileList(filePath, jTextArea);
            }
        }
    }

    /**
     * 读取目录下的所有文件
     *
     * @param filePath 目录
     * @return
     */
    public static void findFileList(String filePath, JTextArea jTextArea) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fileList = file.listFiles();
                if (fileList.length == 0) {

                } else {
                    for (File f : fileList) {
                        if (f.isDirectory()) {
                            findFileList(f.getAbsolutePath(), jTextArea);
                        } else if (f.isFile()) {
                            jTextArea.append(filePath + "\\\\" + f.getName() + "\r\n");
                        } else {
                            System.out.println("未知错误！");
                        }
                    }
                }
            }
        }
    }

    public static JButton selectFileDirButton(JPanel mainPanel, final JTextField selectFileDirString, final JTextArea resultView) {
        JButton selectFolderButton = new JButton("选择文件夹");
        selectFolderButton.setVisible(true);
        selectFolderButton.setBounds(viewWidth - (viewWidth / 4), viewHeight / 12, viewWidth / 6, 30);
        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFileDir(selectFileDirString, resultView);
            }
        });
        mainPanel.add(selectFolderButton);
        return selectFolderButton;
    }

    public static JTextField selectFileDirString(JPanel mainPanel) {
        JTextField selectFolderTextField = new JTextField();
        selectFolderTextField.setVisible(true);
        selectFolderTextField.setBounds(viewWidth / 6, viewHeight / 12, viewWidth / 2, 30);
        mainPanel.add(selectFolderTextField);
        return selectFolderTextField;
    }

    public static JLabel viewInputFilesPath(JPanel mainPanel) {
        JLabel jLabel = new JLabel("当前文件夹路径为：");
        jLabel.setVisible(true);
        jLabel.setBounds(viewWidth / 24, viewHeight / 12, 200, 30);
        mainPanel.add(jLabel);
        return jLabel;
    }

    public static void grandMainPanel(JFrame mainFrame, JPanel mainPanel) {
        // 用于显示所选择的文件夹路径
        final JTextField selectFileDirString = selectFileDirString(mainPanel);
        final JTextArea resultView = resultViewDataBarea(mainPanel);
        JLabel viewInputFilesPath = viewInputFilesPath(mainPanel);
        JLabel viewInputRename = viewInputRename(mainPanel);
        JTextField inputFilesNewNameText = inputFilesNewNameTextField(mainPanel);
        JButton selectFileDirButton = selectFileDirButton(mainPanel, selectFileDirString, resultView);
        JButton reNamesConfirm = reNamesConfirmButton(mainPanel, inputFilesNewNameText, resultView);
        //设置默认布局为null
        mainPanel.setLayout(null);

        // 菜单栏
        JMenuBar menuBar = new JMenuBar();
        JMenu choice = new JMenu("文件");

        // 菜单
        JMenuItem openFile = new JMenuItem("添加文件夹(O)");
        JMenuItem quitApp = new JMenuItem("退出(X)");

        openFile.setMnemonic('O');
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFileDir(selectFileDirString, resultView);
            }
        });
        quitApp.setMnemonic('X');
        quitApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        choice.add(openFile);
        // 设置菜单分隔符
        choice.addSeparator();
        choice.add(quitApp);

        menuBar.add(choice);

        // 设置菜单栏，使用这种方式设置菜单栏可以不占用布局空间
        mainFrame.setJMenuBar(menuBar);
    }

    public static DisplayMode getDisplayMode() {
        /*GraphicsEnvironment 屏幕、打印机或图像缓冲区的屏幕宽度大小*/
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        DisplayMode displayMode = graphicsDevice.getDisplayMode();
        return displayMode;
    }

    public static void main(String[] args) {

        // 创建 JFrame 实例
        JFrame mainFrame = new JFrame("批量命名");
        // Setting the width and height of frame
        mainFrame.setSize(viewWidth, viewHeight);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置居中显示
        mainFrame.setLocationRelativeTo(null);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel mainPanel = new JPanel();

        // 添加面板
        mainFrame.add(mainPanel);

        grandMainPanel(mainFrame, mainPanel);

        // 设置界面可见
        mainFrame.setVisible(true);
    }
}
