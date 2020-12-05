#ifndef FRUIT_HH
#define FRUIT_HH

#include <QMainWindow>
#include <QObject>
#include <QWidget>
#include <QLabel>

class Fruit : public QLabel
{
    Q_OBJECT

public:
    Fruit();

    explicit Fruit(QWidget* parent = Q_NULLPTR, Qt::WindowFlags f = Qt::WindowFlags());
       ~Fruit();

    enum FruitType {CHERRIES,
                     STRAWBERRY,
                     ORANGE,
                     PEAR,
                     APPLE,
                     BANANAS,
                     TOMATO,
                     GRAPES,
                     EGGPLANT,
                     NUMBER_OF_FRUITS};

    FruitType fruitType_;

signals:

    void clicked();

protected:

    void mousePressEvent(QMouseEvent* event);

private:

};

#endif // FRUIT_HH
