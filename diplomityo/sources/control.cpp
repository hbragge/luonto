#include "control.h"
#include <sstream>

CControl::CControl(): symtbl(0), m_dp(0)
{
  content.filepath = "/index.html";
  m_dp = new CDispatcher;
}

CControl::~CControl()
{
  if(symtbl) {
    delete symtbl;
    symtbl = 0;
  }

  if(m_dp) {
    delete m_dp;
    m_dp = 0;
  }
}

Content *CControl::ProcessContent(enum WebUser user, const string action, string objnm, RequestMap *req, CDispatcher *dp)
{
  return (0);

  /*  CAction *acn = 0;
  int acncount = 0;
  error_t error = error_t::WEB_NO_ACTION;

  while (error != error_t::OK && error != home::NO_MORE_OBJECTS && acncount < 2) {
    acn = dp->GetAction(user, action, objnm, *req, error);

    error = acn->ExecuteAction(req, dp);
    acncount++;
  }

  content.filepath = acn->GetViewTpl().c_str();
  symtbl = new CSymbolTable(acn->GetViewData(), objnm, GetMenuState(objnm, *req));
  content.table.symtable = symtbl;

  return &content;*/
}

int CControl::ProcessRequest(CRequest *req)
{
  CAction *acn = 0;
  bool done = false;

  while (!done)
    {
      acn = m_dp->GetAction(req);
      done = acn->Execute(req);
    }

  req->ProcessSymbols(GetMenuState(req->GetObjnm(), *req->GetParams()));

  return (0);
}

// reason out the menu state
string CControl::GetMenuState(const string objnm, RequestMap &req)
{
  string filename = "/sta_menu_default.thtm";
  
  if(objnm.compare("device_interface")==0) {
    filename = "/sta_menu_device_interface_eth.thtm";

    RequestMap::iterator iter = req.begin();
    if((iter = req.find("type")) != req.end()) {
      if ((*iter).second.compare("shdsl")==0 ) {
        
        filename = "/sta_menu_device_interface_shdsl.thtm";
      }
    } 
  } else if(objnm.compare("ip_static_route")==0) {
    filename = "/sta_menu_ip_static_route.thtm";
    
  } else if(objnm.compare("ip_nameserver")==0) {
    filename = "/sta_menu_ip_nameserver.thtm";
    
  } 
  
  return filename;
}
