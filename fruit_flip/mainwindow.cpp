#include "mainwindow.hh"
#include "ui_mainwindow.h"

#include <QKeyEvent>
#include <QDebug>
#include <QPixmap>
#include <QTextBrowser>
#include <QString>
#include <QCursor>
#include <QWidget>
#include <QPoint>
#include <QGraphicsSceneMouseEvent>

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    // We need a graphics scene in which to draw rectangles
    scene_ = new QGraphicsScene(this);

    // The width of the graphicsView is BORDER_RIGHT added by 2,
    // since the borders take one pixel on each side
    // (1 on the left, and 1 on the right).
    // Similarly, the height of the graphicsView is BORDER_DOWN added by 2.
    ui->graphicsView->setGeometry(LEFT_MARGIN, TOP_MARGIN,
                                  BORDER_RIGHT + 2, BORDER_DOWN + 2);
    ui->graphicsView->setScene(scene_);

    // The width of the scene_ is BORDER_RIGHT decreased by 1 and
    // the height of it is BORDER_DOWN decreased by 1, because
    // each square of a fruit is considered to be inside the sceneRect,
    // if its upper left corner is inside the sceneRect.
    scene_->setSceneRect(0, 0, BORDER_RIGHT - 1, BORDER_DOWN - 1);

    int seed = time(0); // You can change seed value for testing purposes
    randomEng_.seed(seed);
    distr_ = std::uniform_int_distribution<int>(0, Fruit::NUMBER_OF_FRUITS - 1);
    distr_(randomEng_); // Wiping out the first random number (which is almost always 0)

    textBrowser = new QTextBrowser(this);

    init_titles();

    fill_grid(COLUMNS,ROWS);

    init_dataview();
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::init_titles()
{
    // Displaying column titles, starting from A
    for(quint32 i = 0, letter = 'A'; i < COLUMNS; ++i, ++letter)
    {
        quint32 shift = 5;
        QString letter_string = "";
        letter_string.append(QString::number(i) + " " + letter);
        QLabel* label = new QLabel(letter_string, this);
        label->setGeometry(LEFT_MARGIN + shift + i * SQUARE_SIDE,
                           TOP_MARGIN - SQUARE_SIDE,
                           SQUARE_SIDE, SQUARE_SIDE);
    }

    // Displaying row titles, starting from A
    for(quint32 i = 0, letter = 'A'; i < ROWS; ++i, ++letter)
    {
        QString letter_string = "";
        letter_string.append( QString::number(i) + " " + letter);
        QLabel* label = new QLabel(letter_string, this);
        label->setGeometry(LEFT_MARGIN - SQUARE_SIDE,
                           TOP_MARGIN + i * SQUARE_SIDE,
                           SQUARE_SIDE, SQUARE_SIDE);
    }
}

void MainWindow::draw_fruit(Fruit& fruit, int xPos_, int yPos_)
{
    // Vector of fruits
    const std::vector<std::string>
            fruits = {"cherries", "strawberry", "orange", "pear", "apple",
                      "bananas", "tomato", "grapes", "eggplant"};

    // Defining where the images can be found and what kind of images they are
    const std::string PREFIX(":/");
    const std::string SUFFIX(".png");

    // Converting image (png) to a pixmap
    std::string filename = PREFIX + fruits.at(fruit.fruitType_) + SUFFIX;
    QPixmap image(QString::fromStdString(filename));

    // Scaling the pixmap
    image = image.scaled(SQUARE_SIDE, SQUARE_SIDE);

    fruit.setGeometry(xPos_, yPos_, SQUARE_SIDE, SQUARE_SIDE);
    fruit.setPixmap(image);
}

void MainWindow::handleButton() {
    Fruit *clabel = (Fruit *)sender();
    if (sel_fruit_ == nullptr) {
        sel_fruit_ = clabel;
        clabel->setFrameStyle(QFrame::Panel | QFrame::Sunken);
    } else {
        if (clabel == sel_fruit_) {
            sel_fruit_ = nullptr;
            clabel->setFrameStyle(QFrame::Plain);
        } else if (fruit_beside(*clabel, *sel_fruit_)) {
            swap_places(*clabel, *sel_fruit_);
            check_grid();
            sel_fruit_->setFrameStyle(QFrame::Plain);
            sel_fruit_ = nullptr;
        }
    }
}

bool MainWindow::fruit_beside(Fruit& /*a*/, Fruit& /*b*/) {
/*
    Index a_ind = get_ind(a);
    Index b_ind = get_ind(b);

    quint32 ax = a_ind.x;
    quint32 ay = a_ind.y;
    quint32 bx = b_ind.x;
    quint32 by = b_ind.y;
*/
    // TODO

    return true;
}

MainWindow::Index MainWindow::get_ind(Fruit& f) {
    quint32 j = 0;
    while (j < fruits_grid_.size()){
        quint32 i = 0;
        while (i < fruits_grid_.at(j).size()) {
            if ((fruits_grid_.at(j).at(i) == &f)) {
                return {i, j};
            }
            ++i;
        }
        ++j;
    }
    assert(false);
    return {999, 999};
}

void MainWindow::swap_places(Fruit& a, Fruit& b) {
    Index a_ind = get_ind(a);
    Index b_ind = get_ind(b);

    quint32 ax = a_ind.x;
    quint32 ay = a_ind.y;
    quint32 bx = b_ind.x;
    quint32 by = b_ind.y;

    Fruit* temp = fruits_grid_.at(ay).at(ax);
    fruits_grid_.at(ay).at(ax) = fruits_grid_.at(by).at(bx);
    fruits_grid_.at(by).at(bx) = temp;
    draw_fruit(b, x_from_idx(ax), y_from_idx(ay));
    draw_fruit(a, x_from_idx(bx), y_from_idx(by));
}

void MainWindow::fill_grid(quint32 columns_, quint32 rows_)
{
    quint32 j = 0;
    std::vector<Fruit*> oneRow;

    while (j < rows_){
        quint32 i = 0;
        fruits_grid_.push_back(oneRow);
        while (i < columns_){
            int r;
            while (true) {
                r = distr_(randomEng_);
                if (((fruits_grid_.at(j).size() < 2) ||
                     (fruits_grid_.at(j).at(i - 1)->fruitType_ != r) ||
                     (fruits_grid_.at(j).at(i - 2)->fruitType_ != r)) &&
                    ((fruits_grid_.size() < 3) ||
                     (fruits_grid_.at(j - 1).at(i)->fruitType_ != r) ||
                     (fruits_grid_.at(j - 2).at(i)->fruitType_ != r))) {
                    break;
                }
            }

            Fruit* fruit = new Fruit(this);
            fruit->fruitType_ = static_cast<Fruit::FruitType>(r);
            connect(fruit, &Fruit::clicked, this, &MainWindow::handleButton);
            draw_fruit(*fruit, x_from_idx(i), y_from_idx(j));
            fruits_grid_.at(j).push_back(fruit);
            ++i;
        }
        ++j;
    }
}

void MainWindow::check_grid() {
    int countx = 0;
    Fruit::FruitType kindx;
    quint32 j = 0;
    while (j < fruits_grid_.size()){
        quint32 i = 1;
        countx = 1;
        kindx = fruits_grid_.at(j).at(0)->fruitType_;
        while (i < fruits_grid_.at(j).size()) {
            if (fruits_grid_.at(j).at(i)->fruitType_ == kindx) {
                ++countx;
            } else {
                if (countx > 2) {
                    printf("found %i\n", countx);
                    assert(false);
                }
                countx = 1;
                kindx = fruits_grid_.at(j).at(i)->fruitType_;
            }
            ++i;
        }
        ++j;
    }
}

void MainWindow::init_dataview()
{
    textBrowser->setGeometry(700, 150, 700, 400);
    textBrowser->setFontPointSize(12);
    update_data();
}

void MainWindow::update_data()
{
/*
    QString qstr;
    std::string str;

    quint32 i = 0;
    quint32 j = 0;

    while (i < fruits_grid_.size()){
        j = 0;
        qstr.append(QString::number(i) + " ");
        while (j < fruits_grid_.at(i).size()) {
            qstr.append((QString::fromStdString(fruits_grid_.at(i).at(j))) + " ");
            ++j;
        }
        textBrowser->append(qstr);
        qstr = "";
        ++i;
    }
*/
}

void MainWindow::on_pushButton_clicked()
{
    ui->pushButton->setText("button pushed");
}
