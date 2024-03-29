#ifndef MAINPROGRAM_HH
#define MAINPROGRAM_HH


#ifdef QT_CORE_LIB
#define GRAPHICAL_GUI
#endif


#include <string>
#include <random>
#include <regex>
#include <chrono>
#include <sstream>
#include <stdexcept>
#include <iostream>
#include <vector>
#include <array>
#include <functional>
#include <utility>
#include <variant>
#include <bitset>

#include "maptrip.hh"

class MainWindow; // In case there's UI

class MainProgram
{
public:
    MainProgram();


    class Stopwatch;

    enum class PromptStyle { NORMAL, NO_ECHO, NO_NESTING };
    enum class TestStatus { NOT_RUN, NO_DIFFS, DIFFS_FOUND };

    bool command_parse_line(std::string input, std::ostream& output);
    void command_parser(std::istream& input, std::ostream& output, PromptStyle promptstyle);

    void setui(MainWindow* ui);

    void flush_output(std::ostream& output);
    bool check_stop() const;

    static int mainprogram(int argc, char* argv[]);

private:
    Maptrip ds_;
    MainWindow* ui_ = nullptr;

    static std::string const PROMPT;

    std::minstd_rand rand_engine_;

    static std::array<unsigned long int, 20> const primes1;
    static std::array<unsigned long int, 20> const primes2;
    unsigned long int prime1_ = 0; // Will be initialized to random value from above
    unsigned long int prime2_ = 0; // Will be initialized to random value from above
    unsigned long int random_places_added_ = 0; // Counter for random stops added
    unsigned long int random_areas_added_ = 0; // Counter for random stops added
    unsigned long int random_ways_added_ = 0; // Counter for random routes added
    void init_primes();
    Name n_to_name(unsigned long int n);
    AreaID n_to_areaid(unsigned long int n);
    WayID n_to_wayid(unsigned long int n);
    PlaceID n_to_placeid(unsigned long int n);


    enum class StopwatchMode { OFF, ON, NEXT };
    StopwatchMode stopwatch_mode = StopwatchMode::OFF;

    enum class ResultType { NOTHING, PLACEIDLIST, AREAIDLIST, ROUTE, WAYS };
    using CmdResultPlaceIDs = std::pair<AreaID, std::vector<PlaceID>>;
    using CmdResultAreaIDs = std::vector<AreaID>;
    using CmdResultRoute = std::vector<std::tuple<Coord, Coord, WayID, Distance>>;
    using CmdResult = std::pair<ResultType, std::variant<CmdResultPlaceIDs, CmdResultAreaIDs, CmdResultRoute>>;
    CmdResult prev_result;
    bool view_dirty = true;

    TestStatus test_status_ = TestStatus::NOT_RUN;

    using MatchIter = std::smatch::const_iterator;
    struct CmdInfo
    {
        std::string cmd;
        std::string info;
        std::string param_regex_str;
        CmdResult(MainProgram::*func)(std::ostream& output, MatchIter begin, MatchIter end);
        void(MainProgram::*testfunc)();
        std::regex param_regex = {};
    };
    static std::vector<CmdInfo> cmds_;
    // Regex objects and their initialization
    std::regex cmds_regex_;
    std::regex coords_regex_;
    std::regex times_regex_;
    std::regex commands_regex_;
    std::regex sizes_regex_;
    void init_regexs();


    CmdResult help_command(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_place_count(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_clear_all(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_all_places(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_add_place(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_place_name_type(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_place_coord(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_add_area(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_area_name(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_area_coords(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_creation_finished(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_find_places_name(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_find_places_type(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_change_place_name(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_change_place_coord(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_all_areas(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_add_subarea_to_area(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_subarea_in_areas(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_all_subareas_in_area(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_places_closest_to(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_common_area_of_subareas(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_remove_place(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_random_add(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_randseed(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_read(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_testread(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_stopwatch(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_perftest(std::ostream& output, MatchIter begin, MatchIter end);
    CmdResult cmd_comment(std::ostream& output, MatchIter begin, MatchIter end);

    void test_random_add();
    void test_place_name_type();
    void test_place_coord();
    void test_find_places_name();
    void test_find_places_type();
    void test_change_place_name();
    void test_change_place_coord();
    void test_area_name();
    void test_subarea_in_areas();
    void test_all_subareas_in_area();
    void test_places_closest_to();
    void test_remove_place();
    void test_common_area_of_subareas();

    void add_random_places_areas(unsigned int size, Coord min = {1,1}, Coord max = {10000, 10000});
    std::string print_place(PlaceID id, std::ostream& output, bool nl = true);
    std::string print_place_name(PlaceID id, std::ostream& output, bool nl = true);
    std::string print_area(AreaID id, std::ostream& output, bool nl = true);
    std::string print_way(WayID id, std::ostream& output, bool nl = true);
    std::string print_coord(Coord coord, std::ostream& output, bool nl = true);

    template <typename Type>
    Type random(Type start, Type end);
    template <typename To>
    static To convert_string_to(std::string from);
    template <typename From>
    static std::string convert_to_string(From from);

    static PlaceType convert_string_to_placetype(std::string from);
    static std::string convert_placetype_to_string(PlaceType type);

    template<PlaceID(Maptrip::*MFUNC)()>
    CmdResult NoParPlaceCmd(std::ostream& output, MatchIter begin, MatchIter end);

    template<std::vector<PlaceID>(Maptrip::*MFUNC)()>
    CmdResult NoParPlaceListCmd(std::ostream& output, MatchIter begin, MatchIter end);

    template<PlaceID(Maptrip::*MFUNC)()>
    void NoParPlaceTestCmd();

    template<std::vector<PlaceID>(Maptrip::*MFUNC)()>
    void NoParPlaceListTestCmd();

    friend class MainWindow;
};

template <typename Type>
Type MainProgram::random(Type start, Type end)
{
    auto range = end-start;
    assert(range != 0 && "random() with zero range!");

    auto num = std::uniform_int_distribution<unsigned long int>(0, range-1)(rand_engine_);

    return static_cast<Type>(start+num);
}

template <typename To>
To MainProgram::convert_string_to(std::string from)
{
    std::istringstream istr(from);
    To result;
    istr >> std::noskipws >> result;
    if (istr.fail() || !istr.eof())
    {
        throw std::invalid_argument("Cannot convert string to required type");
    }
    return result;
}

template <typename From>
std::string MainProgram::convert_to_string(From from)
{
    std::ostringstream ostr;
    ostr << from;
    if (ostr.fail())
    {
        throw std::invalid_argument("Cannot convert type to string");
    }
    return ostr.str();
}

template<PlaceID(Maptrip::*MFUNC)()>
MainProgram::CmdResult MainProgram::NoParPlaceCmd(std::ostream& /*output*/, MatchIter /*begin*/, MatchIter /*end*/)
{
    auto result = (ds_.*MFUNC)();
    return {ResultType::PLACEIDLIST, MainProgram::CmdResultPlaceIDs{NO_AREA, {result}}};
}

template<std::vector<PlaceID>(Maptrip::*MFUNC)()>
MainProgram::CmdResult MainProgram::NoParPlaceListCmd(std::ostream& output, MatchIter /*begin*/, MatchIter /*end*/)
{
    auto result = (ds_.*MFUNC)();
    if (result.empty())
    {
        output << "No stops!" << std::endl;
    }
    return {ResultType::PLACEIDLIST, MainProgram::CmdResultPlaceIDs{NO_AREA, result}};
}

template<PlaceID(Maptrip::*MFUNC)()>
void MainProgram::NoParPlaceTestCmd()
{
    (ds_.*MFUNC)();
}

template<std::vector<PlaceID>(Maptrip::*MFUNC)()>
void MainProgram::NoParPlaceListTestCmd()
{
    (ds_.*MFUNC)();
}


class MainProgram::Stopwatch
{
public:
    using Clock = std::chrono::high_resolution_clock;

    void start() { running_ = true; starttime_ = Clock::now(); }
    void stop() { running_ = false; elapsed_ += (Clock::now() - starttime_); }
    void reset() { running_ = false; elapsed_ = elapsed_.zero(); }
    double elapsed()
    {
        if (!running_)
        {
            return static_cast<std::chrono::duration<double>>(elapsed_).count();
        }
        else
        {
            auto total = elapsed_ + (Clock::now() - starttime_);
            return static_cast<std::chrono::duration<double>>(total).count();
        }
    }
private:
    std::chrono::time_point<Clock> starttime_;
    Clock::duration elapsed_ = Clock::duration::zero();
    bool running_ = false;
};


#endif // MAINPROGRAM_HH
