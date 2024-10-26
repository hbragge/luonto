#include <iostream>
#include <array>
#include <vector>

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
    {V::f, V::u, V::u, V::u, V::g}, // 1st column
    {V::f, V::f, V::f, V::g, V::r},
    {V::r, V::u, V::r, V::u, V::g},
    {V::r, V::u, V::u, V::u, V::g}
}};

struct Point
{
    size_t x;
    size_t y;
};

inline bool operator==(const Point& lhs, const Point& rhs){ return lhs.x == rhs.x && lhs.y == rhs.y; }

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

bool in_range(Point pos)
{
    return (pos.x < X_LEN && pos.y < Y_LEN);
}

std::vector<Point> find_valid_moves(Point pos, Point prev)
{
    cout << "find_valid " << pos.x << "," << pos.y << " " << prev.x << "," << prev.y << " " << endl;
    // enumerate possible moves
    std::vector<Point> moves;
    int steps[3] = {-1,0,1};
    for (auto dx: steps)
    {
        for (auto dy: steps)
        {
            if (dx != 0 ^ dy != 0) // skip diagonal moves
            {
                cout << "check " << dx << " " << dy << endl;
                Point new_pos = {pos.x + dx, pos.y + dy};
                if (new_pos == prev)
                {
                    continue;
                }
                if (in_range(new_pos))
                {
                    if (b[new_pos.x][new_pos.y] == V::f)
                    {
                        cout << "include " << dx << " " << dy << endl;
                        moves.push_back(new_pos);
                    }
                }
            }
            else
            {
                cout << "skip " << dx << " " << dy << endl;
            }
        }
    }
    return moves;
}

bool check_move(const Point p1, const Point p2, const Point prev)
{
    cout << "check_move_p " << p1.x << "," << p1.y << " " << p2.x << "," << p2.y << " " << prev.x << "," << prev.y << endl;
    if (p1 == p2)
    {
        // we found a path
        return true;
    }

    std::vector<Point> valid_moves = find_valid_moves(p1, prev);
    cout << valid_moves.size() << endl;
    if (valid_moves.size() == 0)
    {
        return false;
    }

    for (auto pos: valid_moves)
    {
        if (check_move(pos, p2, p1))
        {
            return true;
        }
    }
    return false;
}

bool check_move(const Point p1, const Point p2) {
    cout << "check_move " << p1.x << "," << p1.y << " " << p2.x << "," << p2.y << " " << endl;
    std::vector<Point> valid_moves = find_valid_moves(p1, {X_LEN, Y_LEN});
    for (auto pos: valid_moves)
    {
      if (check_move(pos, p2, p1))
      {
        return true;
      }
    }
    return false;
}

void move(const Point p1, const Point p2)
{
    if (p1.x > X_LEN - 1 || p2.x > X_LEN - 1 || p1.y > Y_LEN - 1 || p2.y > Y_LEN - 1)
    {
        cout << "ERROR: move: invalid parameter" << endl;
        return;
    }
    if (p1 == p2)
    {
        cout << "ERROR: move: source and dest same" << endl;
        return;
    }

    if (check_move(p1, p2))
    {
        b[p2.x][p2.y] = b[p1.x][p1.y];
        b[p1.x][p1.y] = V::f;
    }

    return;
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
#if 0
    cout << "Invalid moves" << endl;
    // test invalid moves:
    move({0, 0}, {0, 0});
    // nothing changes, and prints error
    print();

    move({1, 4}, {1, 0});
    // nothing happens
    print();

    move({3, 4}, {3, 1});
    // nothing happens
    print();

    move({1, 4}, {1, 3});
    // nothing happens
    print();

    cout << "Valid moves" << endl;
#endif

    move({2, 2}, {0, 0});
    print();

#if 0
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
#endif

    return 0;
}

