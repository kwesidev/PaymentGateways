import hashlib
import requests
import datetime
from collections import OrderedDict
import webbrowser

__author__ = 'kwesidev'

class PayGateWeb(object):

    # paygate server url
    PAY_HOST_SERVER_URL = 'https://secure.paygate.co.za/payweb3/initiate.trans'

    def __init__(self,paygate_id,pay_gate_secret,reference,amount,return_url,email,notify_url):
        """"
          constructor
          :param paygate_id unique id given by paygate
          :param paygate_secret for encryption
          :param reference This is your reference number for use by your internal systems
          :param amount Transaction amount in cents. e.g. 32.99 is specified as 3299
          :param return_url  Once the transaction is completed, PayWeb will return the customer to a page on your web site. The page the customer must see is specified in this field
          :param email the person making the transaction
          :param notify_url if payment is succesful paygate will post back "Post Data" ,so the merchant can mark the order as processed
          :return::
        """
        self.__paygate_id = paygate_id
        self.__reference = reference
        self.__amount = amount
        self.__return_url = return_url
        self.__email = email
        self.__notify_url = notify_url
        self.__pay_gate_secret = pay_gate_secret
        self.__transaction_date = str(datetime.datetime.now())[:19]

    def do_request(self):
        """
        Method  to send request to Paygate and get some values back ,
        this values will be to redirect the user to Paygate Web to make payment
        :param None
        :throws Exception 
        :return PAYGATE_ID,PAY_REQUEST_ID,REFERENCE,CHECKSUM
        """
        req_params = OrderedDict()
        req_params['PAYGATE_ID'] = self.__paygate_id 
        req_params['REFERENCE']  = self.__reference 
        req_params['AMOUNT'] = self.__amount 
        req_params['CURRENCY'] = "ZAR"
        req_params['RETURN_URL'] = self.__return_url,
        req_params['TRANSACTION_DATE'] =  self.__transaction_date
        req_params['LOCALE' ] = "en-za"
        req_params['COUNTRY'] = "ZAF"
        req_params['EMAIL'] = self.__email 
        req_params['NOTIFY_URL'] = self.__notify_url
       
        # encoded_params = urllib.parse.urlencode(req_params).encode()
        # check sum of the parameters
        req_value_string = ""
        for key,value in req_params.items():
            req_value_string += str(value) 
        
        # modify string to remove some values and add paygate secret key
        req_value_string = req_value_string.replace(')','').replace('(','').replace(',','').replace("'","") + str(self.__pay_gate_secret)

        print(req_value_string)
        print('=====================')
        check_sum = hashlib.md5(req_value_string.encode()).hexdigest()
        print(check_sum)
        # store the calculated checksum value
        req_params["CHECKSUM"] = check_sum
         
        # makes request to the actual paygate web host and get response if error occurs stop 
        post_req = requests.post(self.PAY_HOST_SERVER_URL,data = req_params)
        # raise error 
        post_req.raise_for_status()
        result = post_req.text
        # if error is recieved while checking the values throw exception
        if result.startswith("ERROR") :
            raise ValueError(result)

        # return in dictionary format
        dict_res = {}
        for val in result.split('&') :
            val = val.split('=')
            dict_res[str(val[0])] = str(val[1])
        return dict_res        
            return dict_res        
        return dict_res        

    @staticmethod
    def processPayment():
        return

def main():
    print('TESTING PAYGATE WEB')
    
    payment = PayGateWeb("10011072130","secret","REF-103",40,"http://xdevcloud.tk","willzako@aol.com","http://apps.xdevcloud.tk/gateway/notify_payment")
    payment_request = payment.do_request()
    HTML_PAGE = 'paygate.html'
    with open(HTML_PAGE,'w') as html_output :

        html_output.write("<html><head><title>Welcome to My Vps hosting</title></head>")
        html_output.write('<body onload="document.getElementById(\'form1\').submit()">')
        html_output.write('<form action="https://secure.paygate.co.za/payweb3/process.trans" method="POST" id="form1" >')
        html_output.write('<input type="hidden" name="PAY_REQUEST_ID"value="'+ payment_request['PAY_REQUEST_ID'] +'">')
        html_output.write('<input type="hidden" name="CHECKSUM" value="'+payment_request['CHECKSUM']+'">')
        html_output.write('</form>')
        html_output.write('</body>')
        html_output.write('</html>')

            # show user the payment screen
            webbrowser.open(HTML_PAGE)
    except Exception as e:
        print(e)
            
if __name__ == '__main__':
    main()