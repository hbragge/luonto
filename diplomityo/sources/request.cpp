#include "request.h"

CRequest::CRequest(enum WebUser user, const string action, const string objnm, RequestMap *params): m_user(user), m_actionType(action), m_objnm(objnm), m_params(params), m_feedback(false), m_filePath("/index.thtm")  {};

CRequest::~CRequest() {
  Cleanup();
};

void CRequest::Cleanup()
{
  ViewDataList::iterator iter = m_dataList.begin();
  
  if (iter != m_dataList.end()) {
    
    if ((*iter).object != 0) {
      delete (*iter).object;
    }
    iter++;
  }
  m_dataList.clear();
}

void CRequest::SetUser(enum WebUser user)
{
  m_user = user;
}

void CRequest::SetActionType(string type)
{
  m_actionType = type;
}

void CRequest::SetObjnm(string objnm)
{
  m_objnm = objnm;
}

void CRequest::SetParams(RequestMap *params)
{
  m_params = params;
}

void CRequest::SetFeedback()
{
  m_feedback = true;
}

void CRequest::SetFilePath(string tpl)
{
  m_filePath = tpl;
}

void CRequest::SetParamPtr(void *ptr)
{
  paramptr = ptr;
}

void CRequest::SetNull()
{
  m_actionType = "";
  m_objnm = "";
}

void CRequest::ProcessSymbols(string menuState)
{
  m_tbl = new CSymbolTable(m_dataList, m_objnm, menuState);
}

void CRequest::PushData(ViewData data)
{
  m_prevErrFlags = data.errflags;
  m_dataList.push_back(data);
}

const bool CRequest::IsFeedback() const
{
  return m_feedback;
}

const bool CRequest::IsDataEmpty() const
{
  return m_dataList.empty();
}

const enum WebUser CRequest::GetUser() const
{
  return m_user;
}

const string CRequest::GetActionType() const
{
  return m_actionType;
}

const string CRequest::GetPrevActionType() const
{
  return m_prevActionType;
}

const AttrFlags CRequest::GetPrevErrFlags() const
{
  return m_prevErrFlags;
}

const string CRequest::GetObjnm() const
{
  return m_objnm;
}

RequestMap *CRequest::GetParams() const
{
  return m_params;
}

const void *CRequest::GetParamPtr() const
{
  return paramptr;
}

const string CRequest::GetFilePath() const
{
  return m_filePath;
}

CSymbolTable *CRequest::GetSymbolTable() const
{
  return m_tbl;
}
