#ifndef CSYMBOLTABLE_H
#define CSYMBOLTABLE_H

#include <string>
#include <list>
#include <map>
#include "action.h"

class CSymbolTable : public HttpdSymbolTable
{

  string objnm;
  ViewData *m_pData;
  ViewDataList m_ViewData;
  ViewDataList::iterator iter_obj;
  list<string> valueset;
  map<string,string> m_FltAttrMap;
  unsigned char mIncludeDepth;
  HttpdFileHandler::RequestState  mRequestState;
   
  string    mCurAttrnm,
    mCurAttrval,
    mCurFriendlyType,
    mCurMenuState,
    mCurValid,
    mCurValidTok,
    mCurFltAttrnm,
    mCurFltAttrval;
  bool      mCurKey,
    mCurFKey,
    mCurError,
    mCurNotBool,
    mCurChecked,
    mCurSelected,
    mCurIsSet,
    mCurIsNotSet,
    mCurHidden,
    mCurShown;
  
  int       ReturnMenu(HttpdEvalCommand *p_eval, string MenuState);
  
  static inline bool strip(const char c)
    {
      return (c == ' ' || c == '\t');
    }

 public:
  CSymbolTable(ViewDataList &viewdata, string &objnm, string MenuState);

  ~CSymbolTable();
  virtual int HandleCond(HttpdConditionalCommand *p_cond);
  virtual int HandleEval(HttpdEvalCommand *p_eval);
  virtual int HandleLoop(HttpdLoopCommand *p_loop);
};

#endif

