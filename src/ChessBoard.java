import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.swing.*;
import javax.swing.border.*;

//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}

enum PlayerColor {black, white, none}

//Name : Han Seung Hun
//StudentID#: 2014-12614
public class ChessBoard {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");

    ChessBoard() {
        initPieceImages();
        initBoardStatus();
        initializeGui();
    }

    public final void initBoardStatus() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) chessBoardStatus[j][i] = new Piece();
        }
    }

    public final void initPieceImages() {
        pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

        pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    }

    public ImageIcon getImageIcon(Piece piece) {
        if (piece.color.equals(PlayerColor.black)) {
            if (piece.type.equals(PieceType.king)) return pieceImage_b[0];
            else if (piece.type.equals(PieceType.queen)) return pieceImage_b[1];
            else if (piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
            else if (piece.type.equals(PieceType.knight)) return pieceImage_b[3];
            else if (piece.type.equals(PieceType.rook)) return pieceImage_b[4];
            else if (piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
            else return pieceImage_b[6];
        } else if (piece.color.equals(PlayerColor.white)) {
            if (piece.type.equals(PieceType.king)) return pieceImage_w[0];
            else if (piece.type.equals(PieceType.queen)) return pieceImage_w[1];
            else if (piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
            else if (piece.type.equals(PieceType.knight)) return pieceImage_w[3];
            else if (piece.type.equals(PieceType.rook)) return pieceImage_w[4];
            else if (piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
            else return pieceImage_w[6];
        } else return pieceImage_w[6];
    }

    public final void initializeGui() {
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton startButton = new JButton("Reset");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initiateBoard();
            }
        });

        tools.add(startButton);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(new ButtonListener(i, j));
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
                else b.setBackground(Color.gray);
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[j][i] = b;
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    //================================Utilize these functions========================================//

    class Piece {
        PlayerColor color;
        PieceType type;

        Piece() {
            color = PlayerColor.none;
            type = PieceType.none;
        }

        Piece(PlayerColor color, PieceType type) {
            this.color = color;
            this.type = type;
        }
    }

    public void setIcon(int x, int y, Piece piece) {
        chessBoardSquares[y][x].setIcon(getImageIcon(piece));
        chessBoardStatus[y][x] = piece;
    }

    public Piece getIcon(int x, int y) {
        return chessBoardStatus[y][x];
    }

    public void markPosition(int x, int y) {
        chessBoardSquares[y][x].setBackground(Color.pink);
    }

    public void unmarkPosition(int x, int y) {
        if ((y % 2 == 1 && x % 2 == 1) || (y % 2 == 0 && x % 2 == 0))
            chessBoardSquares[y][x].setBackground(Color.WHITE);
        else chessBoardSquares[y][x].setBackground(Color.gray);
    }

    public void setStatus(String inpt) {
        message.setText(inpt);
    }

    public void initiateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) setIcon(i, j, new Piece());
        }
        setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
        setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
        setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
        setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
        for (int i = 0; i < 8; i++) {
            setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
            setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
        }
        setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
        setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
        setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
        setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) unmarkPosition(i, j);
        }
        onInitiateBoard();
    }
//======================================================Don't modify above==============================================================//	


    //======================================================Implement below=================================================================//
    enum MagicType {INITIAL, MARK, CHECK, CHECKMATE}

    ;
    private int selX, selY;
    private boolean check, checkmate, end;
    private PlayerColor turn;
    private MagicType status;
    final Piece piece_null = new Piece();

    // piece_x[0], piece_y[0] : black
    // piece_x[1], piece_y[1] : white
    private int[][] positions = new int[2][16];

    // temporal storage for checkmate()
    Piece piece_tmp;
    int idx_tmp;

    LinkedList<Integer> possibleMove = new LinkedList<>();

    final int[] queen_dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    final int[] queen_dy = {0, 1, 1, 1, 0, -1, -1, -1};
    final int[] rook_dx = {-1, 0, 1, 0};
    final int[] rook_dy = {0, 1, 0, -1};
    final int[] bishop_dx = {-1, 1, 1, -1};
    final int[] bishop_dy = {1, 1, -1, -1};
    final int[] knight_dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    final int[] knight_dy = {1, 2, 2, 1, -1, -2, -2, -1};

    class ButtonListener implements ActionListener {
        int x;
        int y;

        ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e) {    // Only modify here
            // (x, y) is where the click event occured
            if (end) return;
            Piece piece = getIcon(x, y);
            unmarkAll();
            if (piece.color.equals(turn)) {
                mark(x, y);
            } else {
                if (status.equals(MagicType.MARK)) {
                    if (isReachable(selX, selY, x, y)) {
                        move(selX, selY, x, y);
                        check();
                        checkmate();
                        end();
                        changeTurn();
                        setStatusMessage();
                    }
                    status = MagicType.INITIAL;
                }
            }
        }
    }


    void loadPositions() {
        int blackIdx = 0, whiteIdx = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = getIcon(i, j);
                int pos = xy2pos(i, j);
                if (p.color.equals(PlayerColor.black)) {
                    positions[0][blackIdx++] = pos;
                } else if (p.color.equals(PlayerColor.white)) {
                    positions[1][whiteIdx++] = pos;
                }
            }
        }
    }

    int[] getPositions(PlayerColor color) {
        return positions[color2idx(color)];
    }

    void updatePosition(int x_old, int y_old, int x_new, int y_new) {
        Piece p = getIcon(x_old, y_old);
        int[] positions = getPositions(p.color);

        for (int i = 0; i < 16; i++) {
            if (isSamePosition(positions[i], xy2pos(x_old, y_old))) {
                positions[i] = xy2pos(x_new, y_new);
                return;
            }
        }
    }

    void removePosition(int x_old, int y_old) {
        updatePosition(x_old, y_old, -1, -1);
    }

    void move(int x_from, int y_from, int x_to, int y_to) {
        Piece p = getIcon(x_from, y_from);
        if (getIcon(x_to, y_to).color.equals(opponentColor(p.color))) removePosition(x_to, y_to);
        updatePosition(x_from, y_from, x_to, y_to);
        if (status.equals(MagicType.CHECKMATE)) {
            setPiece(x_to, y_to, p);
            setPiece(x_from, y_from, piece_null);
        } else {
            setIcon(x_to, y_to, p);
            setIcon(x_from, y_from, piece_null);
        }
    }

    PlayerColor opponentColor(PlayerColor color) {
        return PlayerColor.values()[1 - color2idx(color)];
    }

    void changeTurn() {
        turn = opponentColor(turn);
    }

    void unmarkAll() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                unmarkPosition(i, j);
            }
        }
    }

    boolean isSamePosition(int pos1, int pos2) {
        return pos1 == pos2;
    }

    boolean isDeadPosition(int pos) {
        return pos == -1;
    }

    boolean isBlankPiece(Piece piece) {
        return piece.color.equals(PlayerColor.none);
    }

    Piece getPiece(int x, int y) {
        try {
            return getIcon(x, y);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    void setPiece(int x, int y, Piece piece) {
        chessBoardStatus[y][x] = piece;
    }

    int color2idx(PlayerColor color) {
        return color.ordinal();
    }

    int xy2pos(int x, int y) {
        return x << 3 | y;
    }

    int pos2x(int i) {
        return (i >> 3) & 0x7;
    }

    int pos2y(int i) {
        return i & 0x7;
    }

    void addPossibleMove(int x, int y) {
        possibleMove.add(xy2pos(x, y));
    }

    void searchPossibleMove(int x, int y) {
        possibleMove.clear();
        Piece piece = getIcon(x, y);
        PieceType type = piece.type;
        PlayerColor color_from = piece.color;
        int x_dir, y_dir, x_temp, y_temp;
        Piece piece_temp;
        if (type.equals(PieceType.king)) {
            for (int idx = 0; idx < queen_dx.length; idx++) {
                x_dir = queen_dx[idx];
                y_dir = queen_dy[idx];
                x_temp = x + x_dir;
                y_temp = y + y_dir;
                piece_temp = getPiece(x_temp, y_temp);
                if (piece_temp == null || piece_temp.color.equals(color_from)) continue;
                addPossibleMove(x_temp, y_temp);
            }
        } else if (type.equals(PieceType.queen)) {
            for (int idx = 0; idx < queen_dx.length; idx++) {
                x_dir = queen_dx[idx];
                y_dir = queen_dy[idx];
                for (int i = 1; i < 8; i++) {
                    x_temp = x + i * x_dir;
                    y_temp = y + i * y_dir;
                    piece_temp = getPiece(x_temp, y_temp);
                    if (piece_temp == null || piece_temp.color.equals(color_from)) break;
                    addPossibleMove(x_temp, y_temp);
                    if (piece_temp.color.equals(opponentColor(color_from))) break;
                }
            }
        } else if (type.equals(PieceType.bishop)) {
            for (int idx = 0; idx < bishop_dx.length; idx++) {
                x_dir = bishop_dx[idx];
                y_dir = bishop_dy[idx];
                for (int i = 1; i < 8; i++) {
                    x_temp = x + i * x_dir;
                    y_temp = y + i * y_dir;
                    piece_temp = getPiece(x_temp, y_temp);
                    if (piece_temp == null || piece_temp.color.equals(color_from)) break;
                    addPossibleMove(x_temp, y_temp);
                    if (piece_temp.color.equals(opponentColor(color_from))) break;
                }
            }
        } else if (type.equals(PieceType.knight)) {
            for (int idx = 0; idx < knight_dx.length; idx++) {
                x_dir = knight_dx[idx];
                y_dir = knight_dy[idx];
                x_temp = x + x_dir;
                y_temp = y + y_dir;
                piece_temp = getPiece(x_temp, y_temp);
                if (piece_temp == null || piece_temp.color.equals(color_from)) continue;
                addPossibleMove(x_temp, y_temp);
            }
        } else if (type.equals(PieceType.rook)) {
            for (int idx = 0; idx < rook_dx.length; idx++) {
                x_dir = rook_dx[idx];
                y_dir = rook_dy[idx];
                for (int i = 1; i < 8; i++) {
                    x_temp = x + i * x_dir;
                    y_temp = y + i * y_dir;
                    piece_temp = getPiece(x_temp, y_temp);
                    if (piece_temp == null || piece_temp.color.equals(color_from)) break;
                    addPossibleMove(x_temp, y_temp);
                    if (piece_temp.color.equals(opponentColor(color_from))) break;
                }
            }
        } else if (type.equals(PieceType.pawn)) {
            int max_dx;
            if (color_from.equals(PlayerColor.black)) {
                x_dir = 1;
                max_dx = x == 1 ? 2 : 1;
            } else {
                x_dir = -1;
                max_dx = x == 6 ? 2 : 1;
            }
            for (int i = 1; i <= max_dx; i++) {
                x_temp = x + i * x_dir;
                y_temp = y;
                piece_temp = getPiece(x_temp, y_temp);
                if (piece_temp != null && isBlankPiece(piece_temp)) {
                    addPossibleMove(x_temp, y_temp);
                } else {
                    break;
                }
            }
            for (int dy = -1; dy <= 1; dy += 2) {
                x_temp = x + x_dir;
                y_temp = y + dy;
                piece_temp = getPiece(x_temp, y_temp);
                if (piece_temp != null && piece_temp.color.equals(opponentColor(color_from)))
                    addPossibleMove(x_temp, y_temp);
            }
        } else {
            //none
        }
    }

    boolean isReachable(int x_from, int y_from, int x_to, int y_to) {
        searchPossibleMove(x_from, y_from);
        return possibleMove.contains(xy2pos(x_to, y_to));
    }

    void mark(int x, int y) {
        searchPossibleMove(x, y);
        for (int pos : possibleMove) {
            markPosition(pos2x(pos), pos2y(pos));
        }
        selX = x;
        selY = y;
        status = MagicType.MARK;
    }

    int findOpponentKing() {
        int[] opponent_positions = getPositions(opponentColor(turn));
        for (int pos : opponent_positions) {
            if (isDeadPosition(pos)) continue;
            int king_x = pos2x(pos);
            int king_y = pos2y(pos);
            if (getIcon(king_x, king_y).type.equals(PieceType.king)) return pos;
        }
        return -1;
    }

    void check() {

        int[] positions = getPositions(turn);

        int king_pos = findOpponentKing();
        int king_x = pos2x(king_pos);
        int king_y = pos2y(king_pos);

        for (int pos : positions) {
            if (isDeadPosition(pos)) continue;
            int x = pos2x(pos);
            int y = pos2y(pos);
            if (check = isReachable(x, y, king_x, king_y)) return;
        }
    }

    void checkmate() {
        if (!check) return;
        status = MagicType.CHECKMATE;
        int[] opponent_positions = getPositions(opponentColor(turn));
        for (int pos : opponent_positions) {
            if (isDeadPosition(pos)) continue;
            selX = pos2x(pos);
            selY = pos2y(pos);
            searchPossibleMove(selX, selY);
            LinkedList<Integer> possibleMoves = (LinkedList<Integer>) possibleMove.clone();
            for (int pos_to : possibleMoves) {
                int x_target = pos2x(pos_to);
                int y_target = pos2y(pos_to);

                savePosition(x_target, y_target);
                move(selX, selY, x_target, y_target);
                check();
                move(x_target, y_target, selX, selY);
                rollbackPosition(x_target, y_target);

                if (!(checkmate = check)) {
                    check = true;
                    return;
                }
            }
        }

    }

    void savePosition(int x, int y) {

        piece_tmp = getIcon(x, y);
        if (!isBlankPiece(piece_tmp)) {
            int[] positions = getPositions(piece_tmp.color);

            for (int i = 0; i < 16; i++) {
                if (isSamePosition(xy2pos(x, y), positions[i])) {
                    idx_tmp = i;
                    break;
                }
            }
        }
    }

    void rollbackPosition(int x, int y) {

        if (!isBlankPiece(piece_tmp)) {
            setPiece(x, y, piece_tmp);
            int[] positions = getPositions(piece_tmp.color);
            positions[idx_tmp] = xy2pos(x, y);
        }
    }

    void end() {
        if (end = checkmate) return;

        int king_pos = findOpponentKing();
        end = isDeadPosition(king_pos);
    }

    void setStatusMessage() {
        String msg = turn.toString().toUpperCase() + "'s TURN";
        if (checkmate) {
            msg += " / CHECKMATE";
        } else if (check) {
            msg += " / CHECK";
        }
        setStatus(msg);
    }

    void onInitiateBoard() {
        check = checkmate = end = false;
        status = MagicType.INITIAL;
        turn = PlayerColor.black;
        setStatusMessage();
        loadPositions();
    }
}