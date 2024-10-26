#include "action.h"
#include "dispatcher.h"
#include "request.h"

CAction::CAction(const string tpl, enum WebUser user, const object_type *pType, string hiddenAttributes, string shownAttributes, string additionalAttributes): 
  m_objnm(""),m_pType(pType),m_user(user),m_tpl(tpl),m_HiddenAttributes(hiddenAttributes),m_ShownAttributes(shownAttributes),m_AdditionalAttributes(additionalAttributes) {

  if (m_pType) {
    m_objnm = m_pType->get_type_name();
  }
  action_type = "base";
}

CAction::~CAction() {}

error_t CAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  req->SetFilePath(m_tpl);

  return error_t::OK;
}

bool CAction::Execute(CRequest *req) const
{
  req->Cleanup();
  error_t error = ExecuteAction(req, m_dp);

  // all ok
  if (error == error_t::OK)
    return true;

  // openadd instead of null show
  if (error == home::NO_MORE_OBJECTS) {
    printf("acn->exa: no more objs\n");
    if (req->GetActionType().compare("show") == 0 && req->IsDataEmpty()) {
      printf("acn->exa: returning openadd\n");
      req->SetActionType("openadd");
      return false;
    } else {
      printf("acn->exa: returning true\n");
      printf("acn->exa: action: %s, tpl: %s, objnm: %s\n",req->GetActionType().c_str(), req->GetFilePath().c_str(), req->GetObjnm().c_str());
      return true;
    }
  }
  // else
  if (error == error_t::WEB_ATTRIBUTE_HANDLING_FAILED)
    {
      printf("acn->exa: attr handling failed\n");
      // add/update needs feedback
      if (GetActionType().compare("add") == 0)
        {
          req->SetFeedback();
          req->SetActionType("openadd");
          return false;

        } else if (GetActionType().compare("update") == 0) {

          req->SetFeedback();
          req->SetActionType("openedit");
          return false;

        }
    } else if (error == error_t::CLI_FORMAT_COMMAND_NODE_NULL) {
      printf("acn->exa: cli_format_comm_node_null\n");
      /* previous action was deleteAction and it went ok
       * -> get saved request from listAction and return it
       */
      req->SetActionType("list");
      return false;
    }
  // something went wrong -> do nothing
  printf("acn->exa: null action\n");
  req->SetNull();

  return true;
}

void CAction::SetObjectType(const object_type *pType)
{
  m_pType = pType;
  
  if (m_pType) {
    m_objnm = m_pType->get_type_name();
  }
}

void CAction::SetDispatcher(CDispatcher *dp)
{
  m_dp = dp;
}

const object_type* CAction::GetObjectType() const
{
  return m_pType;
}

RequestMap CAction::GetRequest() const
{
  return m_request;
}

bool CAction::GetHomePtrAndObjPtr(home*& pHome, obj*& pObj, CDispatcher *dp) const
{

  if (GetObjectType() == NULL)
    return false;
  
  GetObjectType()->create_instance(&pObj);

  if (!pObj)
    return false;

  pHome = dp->GetHomePtr(GetObjectType());
  if (pHome == NULL)
    return false;
  
  return true;
}

ViewDataList &CAction::GetViewData() {
  return m_viewdata;
}

const string CAction::GetViewTpl() const
{
  return m_tpl;
}

error_t CAction::SetAttrValues(ViewData &data, RequestMap *params, const bool strict, const bool onlyKeys, const bool feedback) const
{
  const attr *pTmpAttr = 0;
  
  if (!GetObjectType())
    return error_t::WEB_WRONG_OBJECT_TYPE;
  
  /*  set additional values first
   * (merges req <-> m_AdditionalAttributesMap)
   */
  if(!m_AdditionalAttributesMap.empty())
    {
      printf("acn: setat -> setaddiin\n");
      SetAdditionalValues(*params);
    }
  
  printf("acn: setat -> strict = %i, onlyKeys = %i\n",strict, onlyKeys);
  
  RequestMap::iterator iter = params->begin();

  while(iter != params->end())
    {
      printf("acn: paramsseja\n");
      GetObjectType()->get_attribute((*iter).first,&pTmpAttr);
      
      if(pTmpAttr) {

        // with onlyKeys we process keys only
        if (onlyKeys && !pTmpAttr->is_key_attribute()) {
          printf("acn: setat -> keyfilter skip: %s\n",pTmpAttr->get_attribute_name().c_str());
          iter++;
          continue;
        }
        
        /* if the attr doesn't belong to additional, strict is set 
         * and we don't find the param in m_ShownAttributesMap
         * -> don't bother (no user access)
         */
        if (m_AdditionalAttributesMap.find(pTmpAttr->get_attribute_name()) == m_AdditionalAttributesMap.end()) {
          if (strict && m_ShownAttributesMap.find(pTmpAttr->get_attribute_name()) == m_ShownAttributesMap.end()) {
            printf("acn: setat -> scrictfilter skip: %s\n",pTmpAttr->get_attribute_name().c_str());
            iter++;
            continue;
          }
        }
        const string strVal = (*iter).second;

	const value *val_clone = data.object->construct_attr_value_clone(pTmpAttr);
	if(val_clone) {
          const string valueType = val_clone->get_value_type_friendly_name();
          // initial validation
          if (validate_value(strVal,valueType)) {
            // strVal passed initial validation -> clone
            const value *val_str = val_clone->construct_from_string(strVal);
            if(val_str) {
              // try storing
              if((data.object->store_attr_value_clone(pTmpAttr,val_str)) == error_t::OK)
                {
                  /* ok -> it might be fk/fltattr,
                   * feedbacks doesn't count because
                   * they can be keys but not fk/fltattrs
                   */
                  if (pTmpAttr->is_key_attribute() && !feedback)
                    data.fkflags[pTmpAttr->get_attribute_name()] = true;
                } else {
                  // storing failed -> save errdata
                  data.errflags[pTmpAttr->get_attribute_name()] = true;
                }
              
              delete val_str;
            }
          } else {
            // initial validation failed -> save errdata
            data.errflags[pTmpAttr->get_attribute_name()] = true;
          }
	  delete val_clone;
	}
      }
      iter++;
    }
  
  return error_t::OK;
}

enum WebUser CAction::GetUser() const
{
  return m_user;
}

const string CAction::GetObjnm() const
{
  return m_objnm;
}

string CAction::GetActionType() const
{
  return action_type;
}

bool CAction::validate_value(const string value_str, string valueType) const
{
  pcre *re = 0;
  int rc;
  int ovector[32];

  // XXX: don't care about empty values
  if( value_str.empty() )
    {   
      return true;
    }

  re = m_dp->GetParser()->GetRegularExpressionForAttribute(&valueType);

  if( re == NULL )
    {
      // XXX: no pcre type found, it might be boolean or smth
      return true;
    }
  rc = pcre_exec( re, NULL, value_str.c_str(), value_str.length(), 0, 0, ovector, 32 );
  /*re,          // the compiled pattern
    NULL,        // we didn't study the pattern
    argv[2],     // the subject string
    (int)strlen(argv[2]), // the length of the subject
    0,           // start at offset 0 in the subject
    0,           // default options
    ovector,     // vector for substring information
    OVECCOUNT);  // number of elements in the vector*/
  if( rc < 0 )
    {   
      switch( rc ){
      case PCRE_ERROR_NOMATCH:
        //printf("No match\n");
        break;
      default:
        //printf("Matching error %d\n", rc);
        break;
      }
      
      return false;
    }
  else
    {   
      return true;
    }
}

bool CAction::SetAdditionalValues(RequestMap &req) const
{
  RequestMap::const_iterator iter_add = m_AdditionalAttributesMap.begin();
  RequestMap::iterator iter;
  
  while(iter_add != m_AdditionalAttributesMap.end())
    {
      // if additional key == attrs key -> override
      if((iter = req.find((*iter_add).first)) != req.end())
        {
          printf("acn: setadd -> override\n");
          req.erase(iter);
          req[(*iter_add).first] = (*iter_add).second;

        } else {
          printf("acn: setadd -> append: %s = %s\n", (*iter_add).first.c_str(),(*iter_add).second.c_str());
          // append the rest
          req[(*iter_add).first] = (*iter_add).second;
        }
      iter_add++; 
    }
  return true;
}

bool CAction::ParseHiddenAttributes()
{
  return (ParseVisibilityAttributes(true));
}

bool CAction::ParseShownAttributes()
{
  return (ParseVisibilityAttributes(false));
}

bool CAction::ParseVisibilityAttributes(const bool hidden)
{
  if (hidden) {
    
    if( m_HiddenAttributes.empty() )
      {
        return true;
      }
  } else {
    if( m_ShownAttributes.empty() )
      {
        return true;
      }
  }
  
  int rc;
  int ovector[32];
  string attr;
  
  string tmp;

  if (hidden)
    tmp = m_HiddenAttributes;
  else
    tmp = m_ShownAttributes;

  pcre* re5 = m_dp->GetParser()->GetRegularExpression(RE_STR);
  
  if( re5 == NULL )
  {   
      return false;
  }

  while( tmp.length() > 1 )
  {   
      rc = pcre_exec( re5, NULL, tmp.c_str(), tmp.length(), 0, 0, ovector, 32 );
      
      if( rc < 0 )
      {   
          switch( rc ){
          case PCRE_ERROR_NOMATCH:
            break;
          default:
            break;
          }          
          return false;
      }
      else
      {   
          attr = tmp.substr( ovector[0], ovector[1] - ovector[0] );
          
          tmp = tmp.substr( ovector[1] );
          
          if (hidden)
            m_HiddenAttributesMap[attr] = true;
          else
            m_ShownAttributesMap[attr] = true;
      }
  }
  
  return true;
}

bool CAction::ParseAdditionalAttributes()
{
  if( m_AdditionalAttributes.empty() )
  {   
      return true;
  }
  
  int rc;
  int ovector[32];
  string attrAndVal, attrib, value;
  string tmp = m_AdditionalAttributes;
  pcre* re5 = m_dp->GetParser()->GetRegularExpression(RE_ADDITIONAL_ATTR);
  
  if( re5 == NULL )
  {   
      return false;
  }

  while( tmp.length() > 1 )
  {   
      rc = pcre_exec( re5, NULL, tmp.c_str(), tmp.length(), 0, 0, ovector, 32 );
      
      if( rc < 0 )
      {   
          switch( rc ){
          case PCRE_ERROR_NOMATCH:
            break;
          default:
            break;
          }
          return false;
      }
      else
      { 
          attrAndVal = tmp.substr( ovector[0], ovector[1] - ovector[0] );
          
          attrib = attrAndVal.substr( 1, attrAndVal.find('=') - 1 );
          
          value = attrAndVal.substr( attrAndVal.find('=') + 1, attrAndVal.length() -
                                     attrAndVal.find('=') + 1 );
          
          tmp = tmp.substr( ovector[1] );
          
          m_AdditionalAttributesMap[attrib] = value;
      }
  }

  return true;
}

COpenAddAction::COpenAddAction(const string tpl, enum WebUser user, const object_type *pType, string hiddenAttributes, string additionalAttributes):
  CAction(tpl, user, pType, hiddenAttributes, "", additionalAttributes) 
{
  action_type = "openadd";
}

COpenAddAction::~COpenAddAction() {}

error_t COpenAddAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  ViewData tmpData;

  req->SetFilePath(m_tpl);

  if (GetObjectType() != NULL) {
    
    error_t error = GetObjectType()->create_instance(&tmpData.object);
    
    if(error == error_t::OK) {
      printf("acn: setattiin\n");
      if(SetAttrValues(tmpData, req->GetParams(), false, false, req->IsFeedback()) == error_t::OK) {
        printf("acn: setatt done\n");
        tmpData.errflags = req->GetPrevErrFlags();
        tmpData.hdnflags = m_HiddenAttributesMap;
        printf("acn: hdnflags: %i\n", tmpData.hdnflags.empty());
        req->PushData(tmpData);

        return error_t::OK;
      }
    }
  }
  
  return error_t::WEB_WRONG_OBJECT_TYPE;
}

CDisplaySingleAction::CDisplaySingleAction(const string tpl, enum WebUser user, const object_type *pType, string additionalAttributes):
  CAction(tpl, user, pType, additionalAttributes) {

  action_type = "show";
}

CDisplaySingleAction::~CDisplaySingleAction() {}

error_t CDisplaySingleAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{  
  req->SetFilePath(m_tpl);

  return daccess.FindAll(this, req, dp);
}

CDisplayListAction::CDisplayListAction(const string tpl, enum WebUser user, const object_type *pType, string additionalAttributes):
  CAction(tpl, user, pType, "", "", additionalAttributes)  {

  action_type = "list";
}

CDisplayListAction::~CDisplayListAction() {}

error_t CDisplayListAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{  
  req->SetFilePath(m_tpl);
  
  return daccess.FindAll(this, req, dp);
}

CAddAction::CAddAction(const string tpl, enum WebUser user, const object_type *pType, string shownAttributes, string additionalAttributes):
  CAction(tpl, user, pType, "", shownAttributes, additionalAttributes) {
  
  action_type = "add";
}

CAddAction::~CAddAction() {}

error_t CAddAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  home *pHome;
  ViewData tmpData;
  bool strict = true;
  error_t error = error_t::OK;
 
  req->SetFilePath(m_tpl);
  
  /* if m_ShownAttributesMap is empty,
   * we consider strict (exclusive) attribute
   * handling off (for security reasons we maybe shouldn't)
   */
  if (m_ShownAttributesMap.empty())
    strict = false;

  if (!GetHomePtrAndObjPtr(pHome, tmpData.object, dp)) {

    return error_t::WEB_HOME_OR_FACTORY_NOT_FOUND;
  }
  printf("acn: setattiin\n");
  if ((error = SetAttrValues(tmpData, req->GetParams(), strict, false, req->IsFeedback())) == error_t::OK) {
    printf("acn: setatt ok\n");
    req->PushData(tmpData);
    
    // if no errors -> add and show
    if(tmpData.errflags.empty()) {
      
      printf("acn: no errors -> show\n");
      error = pHome->add(ctx, *tmpData.object);
      // else -> denote error
    } else {
      
      printf("acn: error\n");
      return error_t::WEB_ATTRIBUTE_HANDLING_FAILED;
    }
  }
  return error;
}

CUpdateAction::CUpdateAction(const string tpl, enum WebUser user, const object_type *pType, string additionalAttributes):
  CAction(tpl, user, pType, additionalAttributes) {

  action_type = "update";
}

CUpdateAction::~CUpdateAction() {}

error_t CUpdateAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  home *pHome;
  ViewData tmpData;

  req->SetFilePath(m_tpl);
  
  if (GetHomePtrAndObjPtr(pHome, tmpData.object, dp)) {
    
    error_t error = error_t::OK;
    
    if ((error = SetAttrValues(tmpData, req->GetParams(), false, false, req->IsFeedback())) == error_t::OK) {
      
      req->PushData(tmpData);

      // if no errors -> update
      if(tmpData.errflags.empty()) {

        error = pHome->update(ctx,*tmpData.object);
        // else -> denote error
      } else {

        return error_t::WEB_ATTRIBUTE_HANDLING_FAILED;
      }      
    }
    return error;
    
  } else {
    
    return error_t::WEB_HOME_OR_FACTORY_NOT_FOUND;
  }
}

COpenEditAction::COpenEditAction(const string tpl, enum WebUser user, const object_type *pType, string hiddenAttributes, string additionalAttributes):
  CAction(tpl, user, pType, hiddenAttributes, "", additionalAttributes) {

  action_type = "openedit";
}

COpenEditAction::~COpenEditAction() 
{}

error_t COpenEditAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  req->SetFilePath(m_tpl);

  return daccess.FindAll(this, req, dp);
}

CDeleteAction::CDeleteAction(const string tpl, enum WebUser user, const object_type *pType, string additionalAttributes):
  CAction(tpl, user, pType, additionalAttributes) {

  action_type = "delete";
}

CDeleteAction::~CDeleteAction() 
{}

error_t CDeleteAction::ExecuteAction(CRequest *req, CDispatcher *dp) const
{
  home *pHome = 0;
  ViewData tmpData;

  req->SetFilePath(m_tpl);
  // save request to feedback the list after success
  //  copy(req->begin(), req->end(), inserter(m_request, m_request.begin()));

  error_t error = error_t::OK;

  if(GetHomePtrAndObjPtr(pHome, tmpData.object, dp))
    {
      if ((error = SetAttrValues(tmpData, req->GetParams(), false, false, req->IsFeedback())) == error_t::OK) {
	
	error = pHome->remove(ctx, *tmpData.object);
      } 
    }

  if (tmpData.object)
    delete tmpData.object;

  // object removed -> denote ok
  if (error == error_t::OK)
    return error_t::CLI_FORMAT_COMMAND_NODE_NULL;

  return error;
}
