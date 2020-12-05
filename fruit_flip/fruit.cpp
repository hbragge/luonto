#include "fruit.hh"

Fruit::Fruit(QWidget* parent, Qt::WindowFlags /*f*/) : QLabel(parent) {
}

Fruit::~Fruit() {}

void Fruit::mousePressEvent(QMouseEvent* /*event*/) {

   emit clicked();
}
