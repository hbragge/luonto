#ifndef MAINWINDOW_HH
#define MAINWINDOW_HH

#include "fruit.hh"

#include <QMainWindow>
#include <QGraphicsScene>
#include <QGraphicsRectItem>
#include <vector>
#include <random>
#include <QTimer>
#include <QLabel>
#include <QTextBrowser>
#include <QWidget>
#include <QPoint>
#include <QGraphicsSceneMouseEvent>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

signals:
    // Emitted when user clicks a grid square.
    void mouseClick(int x, int y);

private slots:

    void on_pushButton_clicked();

private:
    Ui::MainWindow *ui;

    // Scene for the game grid
    QGraphicsScene* scene_;

    // Margins for the drawing area (the graphicsView object)
    // You can change the values as you wish
    const quint32 TOP_MARGIN = 150;
    const quint32 LEFT_MARGIN = 100;

    // Size of a square containing a fruit
    const quint32 SQUARE_SIDE = 50; // give your own value here
    // Number of vertical cells (places for fruits)
    const quint32 ROWS = 8; // give your own value here
    // Number of horizontal cells (places for fruits)
    const quint32 COLUMNS = 11; // give your own value here

    // Constants describing scene coordinates
    const quint32 BORDER_UP = 0;
    const quint32 BORDER_DOWN = SQUARE_SIDE * ROWS;
    const quint32 BORDER_LEFT = 0;
    const quint32 BORDER_RIGHT = SQUARE_SIDE * COLUMNS;

    Fruit* sel_fruit_ = 0;

    std::vector<std::vector<Fruit*>> fruits_grid_;

    // debug
    QTextBrowser* textBrowser;
    void init_dataview();
    void update_data();

    // For randomly selecting fruits for the grid
    std::default_random_engine randomEng_;
    std::uniform_int_distribution<int> distr_;

    // Writes the titles for the grid rows and columns
    void init_titles();

    void check_grid();

    void draw_fruit(Fruit& fruit, int xPos, int yPos);

    void fill_grid(quint32 columns_, quint32 rows_);

    void handleButton();

    bool fruit_beside(Fruit&, Fruit&);
    void swap_places(Fruit& a, Fruit& b);

    int x_from_idx(int idx) { return BORDER_LEFT + LEFT_MARGIN + (idx * SQUARE_SIDE); }
    int y_from_idx(int idx) { return BORDER_UP + TOP_MARGIN + (idx * SQUARE_SIDE); }

    struct Index {
        quint32 x;
        quint32 y;
    };

    Index get_ind(Fruit& f);
};
#endif // MAINWINDOW_HH
