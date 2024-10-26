#include <string.h>

#include "seminole.h"
#include "sem_template.h"
#include "control.h"
#include "demo.h"
#include "dispatcher.h"

// DemoHandler constructor

DemoHandler::DemoHandler(
                          HttpdFileSystem *p_filesys,
                          const char      *p_root,
                          const char      *p_prefix
			  ) : HttpdFileHandler(p_filesys,p_root,p_prefix),p_control(0)
{
  // memhomet

  m_pControl = new CControl;
  dp = new CDispatcher;
}

// translate request URI

bool DemoHandler::TranslateUri(RequestState &state)
{
  HttpdCgiParameter *paramhead, *paramcur;
  string action, objnm;
  enum WebUser user = ADMIN;
  RequestMap params;
 
  if(!p_control)
    p_control = new CControl;

  if (state.mpRequest->IsPostRequest()) {
    
    paramhead = paramcur = HttpdCgiParameter::ParsePostData(state.mpRequest);
  } else if (state.mpRequest->IsGetRequest()) {
    
    paramhead = paramcur = HttpdCgiParameter::ParseUriString(state.mpRequest->Query());
  } else {
    
    state.mpRequest->Respond(HTTPD_RESP_METHOD_NOT_ALLOWED);
    return true;
  }

  /*  Parse action and objnm, and save other parameters to *pair.
   *  We dont use Find() because we don't use explicit keys (other than
   * "action" and "objnm") here.
   */
  
  if (paramhead != NULL)
    {
      while (paramcur != NULL)
        {
          if (strcmp("action", paramcur->mPair.mpKey) == 0) {
            
            if (paramcur->mPair.mpValue != NULL) {
              action = string(paramcur->mPair.mpValue);
            } else {
              action = "";
            }

          } else if (strcmp("objnm", paramcur->mPair.mpKey) == 0) {
            
            if (paramcur->mPair.mpValue != NULL) {
              objnm = string(paramcur->mPair.mpValue);
            } else {
              objnm = "";
            }

            //            objnm = paramcur->mPair.mpValue;
            
          } else {
            
            if  (paramcur->mPair.mpValue == NULL) {
              
              params[string(paramcur->mPair.mpKey)] = "";
              
              /*  an ugly way to fix on/off -> yes/no
               *  in addition it doesnt recognize if the
               *  value really is boolean and not just text
               */
            } else if (strcmp(paramcur->mPair.mpValue, "on") == 0) {
              
              params[string(paramcur->mPair.mpKey)] = "yes";
            } else if (strcmp(paramcur->mPair.mpValue, "off") == 0) {
              
              params[string(paramcur->mPair.mpKey)] = "no";
            } else {
              
              params[string(paramcur->mPair.mpKey)] = string(paramcur->mPair.mpValue);
            }
          }
          paramcur = paramcur->mpNext;
        }
    }
  
  m_req = new CRequest(user, action, objnm, &params);
  printf("demo: getting action: %i %s %s\n",m_req->GetUser(), m_req->GetActionType().c_str(), m_req->GetObjnm().c_str());

  m_pControl->ProcessRequest(m_req);
  m_req->SetParamPtr(paramhead);

  state.mpDecodedUri = HttpdUtilities::UriDecode(state.mpReqPath);
  state.mpFilePath = HttpdUtilities::SaveString(m_req->GetFilePath().c_str());
  
  return (state.mpFilePath != NULL);
}
  
// set the corresponding symtable and execute

void DemoHandler::SendFile(RequestState &state)
{
  if (strcmp(state.mFileInfo.MimeType(),"x-server-internal/template") == 0)
    {
      (void)HttpdFSTemplateShell::Execute(state, m_req->GetSymbolTable());

      // cleanup when done
      HttpdCgiParameter::FreeList((HttpdCgiParameter*)m_req->GetParamPtr());

   } else
    state.mpRequest->Respond(HTTPD_RESP_SRV_ERROR);
}
