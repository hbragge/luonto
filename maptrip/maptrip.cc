#include "maptrip.hh"
#include <random>
#include <cmath>
#include<map>

std::minstd_rand rand_engine;

template <typename Type>
Type random_in_range(Type start, Type end)
{
    auto range = end-start;
    ++range;

    auto num = std::uniform_int_distribution<unsigned long int>(0, range-1)(rand_engine);

    return static_cast<Type>(start+num);
}

Maptrip::Maptrip()
{
}

Maptrip::~Maptrip()
{
}

int Maptrip::place_count()
{
    return places_.size();
}

void Maptrip::clear_all()
{
    places_.clear();
}

std::vector<PlaceID> Maptrip::all_places()
{
    std::vector<PlaceID> IDs;
    for (auto& x: places_) {    // koko umappi läpi ja tungetaa
        IDs.push_back(x.first); // vectorii ne ID:t
    }

    return IDs;
}

bool Maptrip::add_place(PlaceID id, const Name& name, PlaceType type, Coord xy)
{
    if (places_.find(id) != places_.end()){
        return false;
    } else {
        std::shared_ptr<Place> new_place
                        = std::make_shared<Place>(Place{});
        new_place->placeID_ = id;
        new_place->name_ = name;
        new_place->placetype_ = type;
        new_place->coord_ = xy;

        places_[id] = new_place;
        return true;
    }
}

std::pair<Name, PlaceType> Maptrip::get_place_name_type(PlaceID id)
{
    if (placeID_not_found(id)){
        return {NO_NAME, PlaceType::NO_TYPE};
    } else {
        return {places_.at(id)->name_, places_.at(id)->placetype_};
    }
}

Coord Maptrip::get_place_coord(PlaceID id)
{
    if (placeID_not_found(id)){
        return NO_COORD;
    } else {
        return places_.at(id)->coord_;
    }
}

bool Maptrip::add_area(AreaID id, const Name &name, std::vector<Coord> coords)
{
    return false;
}

Name Maptrip::get_area_name(AreaID id)
{
    return NO_NAME;
}

std::vector<Coord> Maptrip::get_area_coords(AreaID id)
{
    return {NO_COORD};
}

void Maptrip::creation_finished()
{
}

std::vector<PlaceID> Maptrip::places_alphabetically()
{
    std::multimap<Name, PlaceID> places_sorted_by_name;
    std::vector<PlaceID> placesIDs_sorted_by_name;

    for (auto& x: places_) {
        places_sorted_by_name.insert({x.second->name_, x.first});
        }

    for (auto&y : places_sorted_by_name) {
        placesIDs_sorted_by_name.push_back(y.second);
    }

    return placesIDs_sorted_by_name;
}

std::vector<PlaceID> Maptrip::places_coord_order()
{
    std::multimap<double, PlaceID> places_sorted_by_dist;
    std::vector<PlaceID> placesIDs_sorted_by_dist;

    for (auto& p: places_) {
        double dist_ = sqrt(((p.second->coord_.x*p.second->coord_.x)+
                             (p.second->coord_.y*p.second->coord_.y)));

        places_sorted_by_dist.insert({dist_, p.first});
        }

    for (auto&y : places_sorted_by_dist) {
        std::cout << "dist: " << y.first << " id: " << y.second
                  << std::endl;
        placesIDs_sorted_by_dist.push_back(y.second);
    }

    return placesIDs_sorted_by_dist;
}

std::vector<PlaceID> Maptrip::find_places_name(Name const& name)
{
    std::vector<PlaceID> place_names;

    for (auto& x: places_) {
        if (x.second->name_ == name){
            place_names.push_back(x.first);
        }
    }

    return place_names;
}

std::vector<PlaceID> Maptrip::find_places_type(PlaceType type)
{
    std::vector<PlaceID> place_types;

    for (auto& x: places_) {
        if (x.second->placetype_ == type){
            place_types.push_back(x.first);
        }
      }
    return place_types;
}

bool Maptrip::change_place_name(PlaceID id, const Name& newname)
{
    if (placeID_not_found(id)){
        return false;
    } else {
        places_.at(id)->name_ = newname;
        return true;
    }
}

bool Maptrip::change_place_coord(PlaceID id, Coord newcoord)
{
    if (placeID_not_found(id)){
        return false;
    } else {
        places_.at(id)->coord_ = newcoord;
        return true;
    }
}

std::vector<AreaID> Maptrip::all_areas()
{
    return {};
}

bool Maptrip::add_subarea_to_area(AreaID id, AreaID parentid)
{
    return false;
}

std::vector<AreaID> Maptrip::subarea_in_areas(AreaID id)
{
    return {NO_AREA};
}

std::vector<PlaceID> Maptrip::places_closest_to(Coord xy, PlaceType type)
{
    return {};
}

bool Maptrip::remove_place(PlaceID id)
{
    return false;
}

std::vector<AreaID> Maptrip::all_subareas_in_area(AreaID id)
{
    return {NO_AREA};
}

AreaID Maptrip::common_area_of_subareas(AreaID id1, AreaID id2)
{
    return NO_AREA;
}

bool Maptrip::placeID_not_found(PlaceID id)
{
    if (places_.find(id) == places_.end()){
        return true;
    } else {
        return false;
    }
}
