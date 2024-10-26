#ifndef CREQUEST_H
#define CREQUEST_H

#include <string>
#include <list>
#include "action.h"
#include "symboltable.h"

class CRequest {
 private:
  enum WebUser m_user;
  string m_actionType;
  string m_objnm;
  RequestMap *m_params;
  ViewDataList m_dataList;
  bool m_feedback;
  string m_prevActionType;
  AttrFlags m_prevErrFlags;
  CSymbolTable *m_tbl;
  string m_filePath;
  void *paramptr;
 public:
  CRequest(enum WebUser user, const string action, const string objnm, RequestMap *params);
  ~CRequest();
  void Cleanup();
  void SetUser(enum WebUser user);
  void SetActionType(string type);
  void SetObjnm(string objnm);
  void SetParams(RequestMap *params);
  void SetFeedback();
  void SetFilePath(string tpl);
  void SetParamPtr(void *ptr);
  void SetNull();
  void ProcessSymbols(string menuState);
  void PushData(ViewData data);
  const bool IsFeedback() const;
  const bool IsDataEmpty() const;
  const enum WebUser GetUser() const;
  const string GetActionType() const;
  const string GetPrevActionType() const;
  const AttrFlags GetPrevErrFlags() const;
  const string GetObjnm() const;
  const string GetFilePath() const;
  const void *GetParamPtr() const;
  RequestMap *GetParams() const;
  CSymbolTable *GetSymbolTable() const;
};

#endif
