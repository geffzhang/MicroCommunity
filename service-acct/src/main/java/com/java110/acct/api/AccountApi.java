package com.java110.acct.api;

import com.java110.acct.bmo.account.IGetAccountBMO;
import com.java110.dto.account.AccountDto;
import com.java110.dto.accountDetail.AccountDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AccountApi
 * @Description TODO
 * @Author wuxw
 * @Date 2021/5/4 12:44
 * @Version 1.0
 * add by wuxw 2021/5/4
 **/

@RestController
@RequestMapping(value = "/account")
public class AccountApi {

    @Autowired
    private IGetAccountBMO getAccountBMOImpl;

    /**
     * 微信删除消息模板
     *
     * @param storeId 小区ID
     * @return
     * @serviceCode /account/queryAccount
     * @path /app/account/queryAccount
     */
    @RequestMapping(value = "/queryAccount", method = RequestMethod.GET)
    public ResponseEntity<String> queryAccount(@RequestHeader(value = "store-id", required = false) String storeId,
                                               @RequestParam(value = "page") int page,
                                               @RequestParam(value = "row") int row) {
        AccountDto accountDto = new AccountDto();
        accountDto.setPage(page);
        accountDto.setRow(row);
        accountDto.setObjId(storeId);
        return getAccountBMOImpl.get(accountDto);
    }

    /**
     * 查询账户明细
     *
     * @param storeId 小区ID
     * @return
     * @serviceCode /account/queryAccountDetail
     * @path /app/account/queryAccountDetail
     */
    @RequestMapping(value = "/queryAccountDetail", method = RequestMethod.GET)
    public ResponseEntity<String> queryAccountDetail(@RequestHeader(value = "store-id", required = false) String storeId,
                                                     @RequestParam(value = "acctId", required = false) String acctId,
                                                     @RequestParam(value = "page") int page,
                                                     @RequestParam(value = "row") int row) {
        AccountDetailDto accountDto = new AccountDetailDto();
        accountDto.setPage(page);
        accountDto.setRow(row);
        accountDto.setObjId(storeId);
        accountDto.setAcctId(acctId);
        return getAccountBMOImpl.getDetail(accountDto);
    }
}
