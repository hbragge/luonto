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

size_t find_cross_x(const size_t from_y, const size_t to_y)
{
    size_t x = 0;
    while (x < X_LEN)
    {
        if (b[x][from_y] != V::u && b[x][to_y] == V::f)
        {
            return x;
        }
        ++x;
    }
    cout << "ERROR: find_cross_x: no crossing found" << endl;
    return X_LEN;
}

bool has_path(
    const Point p1,
    const Point p2,
    const bool at_start = false)
{
    if (!at_start && b[p1.x][p1.y] != V::f)
    {
        cout << "ERROR: has_path: no path found (" << p1.x << "," << p1.y << "," << p2.x << "," << p2.y << ")" << endl;
        return false;
    }
    if (p1.y < p2.y)
    {
        // find crossing south
        auto cx = find_cross_x(p1.y, p1.y + 1);
        if (cx == X_LEN)
        {
            // no crossing
            return false;
        }
        if (p1.x < cx)
        {
            // towards crossing, right
            return has_path({p1.x + 1, p1.y}, p2);
        }
        else if (p1.x > cx)
        {
            // towards crossing, left
            return has_path({p1.x - 1, p1.y}, p2);
        }
        else
        {
            // go south
            return has_path({p1.x, p1.y + 1}, p2);
        }
    }
    else if (p1.y > p2.y)
    {
        // find crossing north
        auto cx = find_cross_x(p1.y, p1.y - 1);
        if (cx == X_LEN)
        {
            return false;
        }
        if (p1.x < cx)
        {
            return has_path({p1.x + 1, p1.y}, p2);
        }
        else if (p1.x > cx)
        {
            return has_path({p1.x - 1, p1.y}, p2);
        }
        else
        {
            // go north
            return has_path({p1.x, p1.y - 1}, p2);
        }
    }
    else
    {
        // on same row
        if (p1.x < p2.x)
        {
            return has_path({p1.x + 1, p1.y}, p2);
        }
        else if (p1.x > p2.x)
        {
            return has_path({p1.x - 1, p1.y}, p2);
        }
        else
        {
            // done
            return true;
        }
    }
}

void move(const Point p1, const Point p2)
{
    if (p1.x > X_LEN - 1 || p2.x > X_LEN - 1 || p1.y > Y_LEN - 1 || p2.y > Y_LEN - 1)
    {
        cout << "ERROR: move: invalid parameter" << endl;
        return;
    }
    if (p1.x == p2.x && p1.y == p2.y)
    {
        cout << "ERROR: move: source and dest same" << endl;
        return;
    }
    auto src = b[p1.x][p1.y];
    if (src == V::u || src == V::f)
    {
        cout << "ERROR: move: source position has no piece" << endl;
        return;
    }
    auto dst = b[p2.x][p2.y];
    if (dst != V::f)
    {
        cout << "ERROR: move: destination is not free" << endl;
        return;
    }

    if (has_path(p1, p2, true))
    {
        b[p2.x][p2.y] = src;
        b[p1.x][p1.y] = V::f;
    }
    else
    {
        cout << "ERROR: move: invalid move" << endl;
    }
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

    return 0;
}
