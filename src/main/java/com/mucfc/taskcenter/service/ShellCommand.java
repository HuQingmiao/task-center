package com.mucfc.taskcenter.service;

import com.mucfc.taskcenter.common.AbsCommnad;
import com.mucfc.taskcenter.common.BasicService;
import com.mucfc.taskcenter.common.ICommand;
import com.mucfc.taskcenter.dao.AppCallLogDao;
import com.mucfc.taskcenter.dao.ScheduleControlDao;
import com.mucfc.taskcenter.vo.AppCallLog;
import com.mucfc.taskcenter.vo.AppReg;
import com.mucfc.taskcenter.vo.ScheduleControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Shell命令类
 * <p/>
 * Created by HuQingmiao on 2015-5-14.
 */

@Service
@Scope("prototype")
public class ShellCommand extends AbsCommnad{


}
