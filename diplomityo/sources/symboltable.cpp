#include "symboltable.h"
#include <algorithm>
#include "StreamTokenizer.h"

CSymbolTable::CSymbolTable(ViewDataList &viewdata, string &objnm, string MenuState):
  objnm(objnm), m_ViewData(viewdata), mCurMenuState(MenuState)
{
  iter_obj = m_ViewData.begin();
}

CSymbolTable::~CSymbolTable() {}

int CSymbolTable::HandleCond(HttpdConditionalCommand *p_cond)
{
  assert(p_cond != NULL);

  const char *name = p_cond->Name();

  if (strcmp(name,"key")==0) {
    return (ReturnBool(mCurKey));
    /*  } else if (strcmp(name,"filter")==0) {
        return (ReturnBool(mCurFlt));*/
  } else if (strcmp(name,"fkey")==0 || strcmp(name,"filter")==0) {
    return (ReturnBool(mCurFKey));
  } else if (strcmp(name,"notbool")==0) {
    return (ReturnBool(mCurNotBool));
  } else if (strcmp(name,"checked")==0) {
    return (ReturnBool(mCurChecked));
  } else if (strcmp(name,"isset")==0) {
    return (ReturnBool(mCurIsSet));
  } else if (strcmp(name,"isnotset")==0) {
    return (ReturnBool(mCurIsNotSet));
  } else if (strcmp(name,"selected")==0) {
    return (ReturnBool(mCurSelected));
  } else if (strcmp(name,"error")==0) {
    return (ReturnBool(mCurError));
  } else if (strcmp(name,"hidden")==0) {
    return (ReturnBool(mCurHidden));
  } else if (strcmp(name,"shown")==0) {
    return (ReturnBool(mCurShown));
  } else {
    return (HTTPD_TEMPLATE_UNKNOWN_NAME);
  }  
}

int CSymbolTable::HandleEval(HttpdEvalCommand *p_eval)
{
  assert(p_eval != NULL);
  
  const char *p_name = p_eval->Name();
  
  if (strcmp(p_name,"menu") == 0) {

    return (ReturnMenu(p_eval, mCurMenuState));

  } else if (strcmp(p_name,"objnm") == 0) {
    
    return (p_eval->Output()->WriteString(objnm.c_str()));
    
  } else if (strcmp(p_name,"name") == 0) {
    
    return (p_eval->Output()->WriteString(mCurAttrnm.c_str()));
    
  } else if (strcmp(p_name,"value") == 0) {
    
    return (p_eval->Output()->WriteString(mCurAttrval.c_str()));
    
  } else if (strcmp(p_name,"type") == 0) {
      
    if (!mCurNotBool) {
      
      return (p_eval->Output()->WriteString("checkbox"));
    } else {
      
      return (p_eval->Output()->WriteString("text"));
    } 
    
  } else if (strcmp(p_name,"format") == 0) {
    
    return (p_eval->Output()->WriteString(mCurValid.c_str()));
    
  } else if (strcmp(p_name,"tok") == 0) {
    
    return (p_eval->Output()->WriteString(mCurValidTok.c_str()));

  } else if (strcmp(p_name,"fltname") == 0) {
    
    return (p_eval->Output()->WriteString(mCurFltAttrnm.c_str()));

  } else if (strcmp(p_name,"fltvalue") == 0) {
    
    return (p_eval->Output()->WriteString(mCurFltAttrval.c_str()));
    
  }  else {
    return (HTTPD_TEMPLATE_NOT_HANDLED);
  }  	     
}
	     
int CSymbolTable::HandleLoop(HttpdLoopCommand *p_loop)
{
  assert(p_loop != NULL);

  /*  if(iter_obj == m_ViewData.end())
      return (0);*/

  HttpdLoopCounterSymbols lcs(p_loop);

  /*  if (strcmp(p_loop->Name(), "objs") == 0)
    {
      while (iter_obj != m_ViewData.end()) {
        // update name (the object type may vary)
        objnm = (*iter_obj).object->get_object_type()->get_type_name().c_str();
	p_loop->Iterate();
        
        iter_obj++;
        lcs.bias =+ 6;
      }

      return (0);
      }*/

  if (strcmp(p_loop->Name(), "objs") == 0)
    {
      while (iter_obj != m_ViewData.end()) {
        // update name (the object type may vary)
        objnm = (*iter_obj).object->get_object_type()->get_type_name();
	p_loop->Iterate();
        
        iter_obj++;
        lcs.bias =+ 6;
      }

      return (0);
    }

  if (strcmp(p_loop->Name(), "attrs") == 0)
    {
      if(iter_obj == m_ViewData.end())
        return (0);

      if((*iter_obj).object == NULL)
        return (0);
      
      object_type::attribute_iterator attrlist_iter;
      
      attrlist_iter = (*iter_obj).object->get_object_type()->get_all_attributes().begin();

      while (attrlist_iter != (*iter_obj).object->get_object_type()->get_all_attributes().end()) {
	
        mCurNotBool = true;
        mCurChecked = false;
        mCurKey = false;
        //        mCurFlt = false;
        mCurFKey = false;
        mCurError = false;
        mCurIsSet = false;
        mCurIsNotSet = true;
        mCurHidden = false;
        mCurShown = false;
        
	if((*attrlist_iter) != NULL ) {
	  
	  mCurAttrnm = (*attrlist_iter)->get_attribute_name();

          const value *val_clone = (*iter_obj).object->construct_attr_value_clone(*attrlist_iter);
	  mCurAttrval = val_clone->get_string_value();
          delete val_clone;

	  mCurValid = (*attrlist_iter)->get_validation_rule_description();
	  mCurKey = (*attrlist_iter)->is_key_attribute();
	  mCurFriendlyType = (*attrlist_iter)->get_default_value()->get_value_type_friendly_name();
          
          //          mCurFlt = ((*iter_obj).fltflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).fltflags.end()) ? true : false;
          //          mCurFKey = ((*iter_obj).fkflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).fkflags.end()) ? true : false;
          mCurError = ((*iter_obj).errflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).errflags.end()) ? true : false;
          mCurHidden = ((*iter_obj).hdnflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).hdnflags.end()) ? true : false;
          mCurShown = ((*iter_obj).shwflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).shwflags.end()) ? true : false;

          if ((*iter_obj).fkflags.find((*attrlist_iter)->get_attribute_name()) != (*iter_obj).fkflags.end())
            {
              mCurFKey = true;
              printf("asetetaa fk: %s = %s\n",(*attrlist_iter)->get_attribute_name().c_str(), mCurAttrval.c_str());
              m_FltAttrMap[(*attrlist_iter)->get_attribute_name()] = mCurAttrval;
            }

          // tokenize description if possible
          istringstream is(mCurValid);
          StreamTokenizer st(is, "|");

          string tok;
          int tok_count = 0;
          while ((tok=st.next()) != "" ) {
            // insert into set
            tok_count++;
            tok.erase(std::remove_if(tok.begin(),tok.end(),strip),tok.end());
            valueset.push_back(tok);
          }
          
          if (tok_count >= 2) {
            mCurIsSet = true;
            mCurIsNotSet = false;
            p_loop->Iterate();
            attrlist_iter++;
            continue;
          } else {
            valueset.clear();
          }
          
          //          mCurFKey = (mCurKey && (*iter_obj).object->has_nondefault_value(*attrlist_iter)) ? true : false;

	  if (mCurFriendlyType == "Boolean") {
	    mCurNotBool = false;
	    mCurChecked = (mCurAttrval.compare("yes")==0) ? true : false;
	  }

	  p_loop->Iterate();
	  
	}	
	attrlist_iter++;
      }

      return (0);
    }
  
  if (strcmp(p_loop->Name(), "set") == 0)
    {
      list<string>::const_iterator iter = valueset.begin();    

      while(iter != valueset.end()) {
        mCurSelected = false;
        mCurValidTok = *iter;
        
        // XXX: is it safe to compare to mCurAttrval?
        mCurSelected = (mCurValidTok.compare(mCurAttrval)==0) ? true : false;

        p_loop->Iterate();
        iter++;
      }
      valueset.clear();

      return (0);
    }
  
  if (strcmp(p_loop->Name(), "fltattrs") == 0)
    {
      map<string,string>::iterator iter_flt = m_FltAttrMap.begin();
      printf("cs: fltattrs\n");
      while (iter_flt != m_FltAttrMap.end()) {
        printf("cs: mapissa tavaraa: %s = %s\n", (*iter_flt).first.c_str(), (*iter_flt).second.c_str());
        mCurFltAttrnm = (*iter_flt).first;
        mCurFltAttrval = (*iter_flt).second;
        //        m_FltAttrMap.erase(iter_flt);
        p_loop->Iterate();
        iter_flt++;
      }
      return (0);
    }
  
  return (HTTPD_TEMPLATE_NOT_HANDLED);
}

// include menu from MenuState-file
int CSymbolTable::ReturnMenu(HttpdEvalCommand *p_eval, string MenuState)
{
  int rc = HttpdOpSys::ERR_BADPARAM;

      HttpdFSTemplateShell *p_shell;
      HttpdTemplateProcessor *p_processor;
  
      p_processor = p_eval->Processor();
      p_shell = (HttpdFSTemplateShell *)p_processor->Top();
      HttpdFileHandler::RequestState &rs = p_shell->State();

      HttpdFileSystem *p_fsys = rs.mpHandler->FileSystem();
      HttpdFile       *p_file;
      
      rc = p_fsys->Open(MenuState.c_str(),
                        HttpdFileSystem::FILE_READ_ONLY,
                        p_file);
      
      if (httpd_often(rc == 0))
        { 
          // Process nested template.
          HttpdTemplateProcessor clone(p_processor, false);
          rc = clone.StartProcessing(p_file, *p_processor);
          p_file->Close();
          
        }
      return (rc);
}

