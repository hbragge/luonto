#ifndef MAPTRIP_HH
#define MAPTRIP_HH

#include <string>
#include <vector>
#include <tuple>
#include <utility>
#include <limits>
#include <functional>
#include <memory>
#include <math.h>
#include <iostream>

using PlaceID = long int;
using AreaID = long int;
using Name = std::string;
using WayID = std::string;

PlaceID const NO_PLACE = -1;
AreaID const NO_AREA = -1;
WayID const NO_WAY = "!!No way!!";

int const NO_VALUE = std::numeric_limits<int>::min();

Name const NO_NAME = "!!NO_NAME!!";

enum class PlaceType { OTHER=0, FIREPIT, SHELTER, PARKING, PEAK, BAY, AREA, NO_TYPE };

struct Coord
{
    int x = NO_VALUE;
    int y = NO_VALUE;
};

inline bool operator==(Coord c1, Coord c2) { return c1.x == c2.x && c1.y == c2.y; }
inline bool operator!=(Coord c1, Coord c2) { return !(c1==c2); }

Coord const NO_COORD = {NO_VALUE, NO_VALUE};

using Distance = int;

Distance const NO_DISTANCE = NO_VALUE;

class Maptrip
{
public:
    Maptrip();
    ~Maptrip();

    int place_count();
    void clear_all();
    std::vector<PlaceID> all_places();
    bool add_place(PlaceID id, Name const& name, PlaceType type, Coord xy);
    std::pair<Name, PlaceType> get_place_name_type(PlaceID id);
    Coord get_place_coord(PlaceID id);

    std::vector<PlaceID> places_alphabetically();
    std::vector<PlaceID> places_coord_order();
    std::vector<PlaceID> find_places_name(Name const& name);
    std::vector<PlaceID> find_places_type(PlaceType type);

    bool change_place_name(PlaceID id, Name const& newname);
    bool change_place_coord(PlaceID id, Coord newcoord);
    bool add_area(AreaID id, Name const& name, std::vector<Coord> coords);
    Name get_area_name(AreaID id);

    std::vector<Coord> get_area_coords(AreaID id);
    std::vector<AreaID> all_areas();
    bool add_subarea_to_area(AreaID id, AreaID parentid);
    std::vector<AreaID> subarea_in_areas(AreaID id);

    void creation_finished();
    std::vector<AreaID> all_subareas_in_area(AreaID id);
    std::vector<PlaceID> places_closest_to(Coord xy, PlaceType type);
    bool remove_place(PlaceID id);
    AreaID common_area_of_subareas(AreaID id1, AreaID id2);

private:
    struct Place
    {
        PlaceID placeID_;
        Name name_ ;
        PlaceType placetype_;
        Coord coord_;
    };

    std::unordered_map<int,std::shared_ptr<Place>> places_;
    bool placeID_not_found(PlaceID id);
};

#endif // MAPTRIP_HH
