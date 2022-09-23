#include <iostream>
#include <array>

using std::cout;
using std::endl;

enum class V {
    u, // unused
    f, // free
    r, // red
    g  // green
};

static const size_t X_LEN = 4;
static const size_t Y_LEN = 5;
typedef std::array<std::array<V, Y_LEN>, X_LEN> Board;
Board b = {{
    {V::f, V::u, V::u, V::u, V::g}, // 1st column, ie. b[0][y]
    {V::f, V::f, V::f, V::g, V::r}, // b[1][y] ...
    {V::r, V::u, V::r, V::u, V::g},
    {V::r, V::u, V::u, V::u, V::g}
}};

struct Point
{
    size_t x;
    size_t y;
};

void print_val(V v)
{
    switch (v)
    {
    case V::u:
        {
            cout << ' ';
            break;
        }
    case V::f:
        {
            cout << '.';
            break;
        }
    case V::r:
        {
            cout << 'R';
            break;
        }
    case V::g:
        {
            cout << 'G';
            break;
        }
    }
 }

void print()
{
    cout << "   ";
    for (size_t x = 0; x < X_LEN; ++x)
    {
        cout << x;
        cout << ' ';
    }
    cout << endl;
    for (size_t y = 0; y < Y_LEN; ++y)
    {
        cout << y;
        cout << ' ';
        for (size_t x = 0; x < X_LEN; ++x)
        {
            cout << ' ';
            print_val(b[x][y]);
        }
        cout << endl;
    }
    cout << endl;
}

void move(const Point p1, const Point p2)
{
    if (p1.x > X_LEN - 1 || p2.x > X_LEN - 1 || p1.y > Y_LEN - 1 || p2.y > Y_LEN - 1)
    {
        cout << "ERROR: move: invalid parameter" << endl;
        return;
    }

    // TODO
}

int main()
{
    print();
/*
   0 1 2 3
0  . . R R
1    .
2    . R
3    G
4  G R G G
*/

    move({2, 2}, {0, 0});
    print();
/*
   0 1 2 3
0  R . R R
1    .
2    . .
3    G
4  G R G G
*/

    move({1, 3}, {2, 2});
    print();
/*
   0 1 2 3
0  R . R R
1    .
2    . G
3    .
4  G R G G
*/

    move({1, 4}, {1, 0});
    print();
/*
   0 1 2 3
0  R R R R
1    .
2    . G
3    .
4  G . G G
*/

    move({2, 2}, {1, 4});
    print();
/*
   0 1 2 3
0  R R R R
1    .
2    . .
3    .
4  G G G G
*/

    move({0, 0}, {0, 1});
    print();

    return 0;
}
