#include "dataaccess.h"
#include "action.h"
#include "request.h"
#include "dispatcher.h"

CDataAccess::CDataAccess() {}

CDataAccess::~CDataAccess() {}

error_t CDataAccess::FindAll(const CAction *acn, CRequest *req, CDispatcher *dp) const
{
  home *pHome;
  obj *pFilterObj = 0;
  ViewData tmpData;
  
  error_t error = error_t::OK;
  
  if (!acn->GetHomePtrAndObjPtr(pHome, pFilterObj, dp)) {
    
    return error_t::WEB_HOME_OR_FACTORY_NOT_FOUND;
  }
  tmpData.object = pFilterObj;

  if ((error = acn->SetAttrValues(tmpData, req->GetParams(), false, true, req->IsFeedback())) == error_t::OK) {
    
    obj *pResultObj = pFilterObj->clone();
    obj *pPrevObj = 0;
    
    // get the first object
    error = pHome->get_first(acn->ctx, *pFilterObj, *pResultObj);
    
    // if no objects
    if (error == home::NO_MORE_OBJECTS) {
      
      delete pFilterObj;
      delete pResultObj;
      return error;
    }
    tmpData.object = pResultObj->clone();

    // get errors and hdnflags from previous -- openedit needs them
    tmpData.errflags = req->GetPrevErrFlags();
    tmpData.hdnflags = acn->m_HiddenAttributesMap;

    req->PushData(tmpData);

    // more than one object (needed for a list)
    while(error == error_t::OK) {
      
      if (pPrevObj != 0)
        {
          delete pPrevObj;
          pPrevObj = 0;
        }
      pPrevObj = pResultObj->clone();
      error = pHome->get_next(acn->ctx, *pFilterObj, *pPrevObj, *pResultObj);
      tmpData.object = pResultObj->clone();
      if (error == error_t::OK) {
        
        req->PushData(tmpData);
      }      
    }
    
    // cleaning
    if (pPrevObj != 0)
      {
        delete pPrevObj;
        pPrevObj = 0;
      }
    delete tmpData.object;
    delete pResultObj;
    delete pFilterObj;
    
    if (error == error_t::OK || error == home::NO_MORE_OBJECTS) {
      
      return error;
    } else {
      
      return error_t::UNSPECIFIED_ERROR;
    }
  }
  return error;
}
