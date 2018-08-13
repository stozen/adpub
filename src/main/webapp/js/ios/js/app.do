"use strict";var _typeof2="function"==typeof Symbol&&"symbol"==typeof Symbol.iterator?function(t){return typeof t}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":typeof t};String.prototype.contains=String.prototype.contains||function(t){return 0<=this.indexOf(t)},String.prototype.startsWith=String.prototype.startsWith||function(t){return 0===this.indexOf(t)},String.prototype.endsWith=String.prototype.endsWith||function(t){return 0<=this.indexOf(t,this.length-t.length)},function(o){var i={},s=o.require;o.require=function(t,e,a){try{var r,n=t;return n.startsWith("./")&&(n=n.slice(2)),n.endsWith(".do")||(n+=".do"),e||a?r=s.apply(this,arguments):"function"!=typeof(r=i[n])&&(r=s.apply(this,arguments),i[n]=r),r?r(o):function(){}}catch(t){throw console.log("e="+t),delete i[n],t}},o.AddModule=function(t,e){i[t]=e},function(t){function u(t,e){var a=(65535&t)+(65535&e);return(t>>16)+(e>>16)+(a>>16)<<16|65535&a}function s(t,e,a,r,n,o){return u((i=u(u(e,t),u(r,o)))<<(s=n)|i>>>32-s,a);var i,s}function f(t,e,a,r,n,o,i){return s(e&a|~e&r,t,e,n,o,i)}function p(t,e,a,r,n,o,i){return s(e&r|a&~r,t,e,n,o,i)}function h(t,e,a,r,n,o,i){return s(e^a^r,t,e,n,o,i)}function y(t,e,a,r,n,o,i){return s(a^(e|~r),t,e,n,o,i)}function d(t,e){t[e>>5]|=128<<e%32,t[14+(e+64>>>9<<4)]=e;var a,r,n,o,i,s=1732584193,d=-271733879,l=-1732584194,c=271733878;for(a=0;a<t.length;a+=16)d=y(d=y(d=y(d=y(d=h(d=h(d=h(d=h(d=p(d=p(d=p(d=p(d=f(d=f(d=f(d=f(n=d,l=f(o=l,c=f(i=c,s=f(r=s,d,l,c,t[a],7,-680876936),d,l,t[a+1],12,-389564586),s,d,t[a+2],17,606105819),c,s,t[a+3],22,-1044525330),l=f(l,c=f(c,s=f(s,d,l,c,t[a+4],7,-176418897),d,l,t[a+5],12,1200080426),s,d,t[a+6],17,-1473231341),c,s,t[a+7],22,-45705983),l=f(l,c=f(c,s=f(s,d,l,c,t[a+8],7,1770035416),d,l,t[a+9],12,-1958414417),s,d,t[a+10],17,-42063),c,s,t[a+11],22,-1990404162),l=f(l,c=f(c,s=f(s,d,l,c,t[a+12],7,1804603682),d,l,t[a+13],12,-40341101),s,d,t[a+14],17,-1502002290),c,s,t[a+15],22,1236535329),l=p(l,c=p(c,s=p(s,d,l,c,t[a+1],5,-165796510),d,l,t[a+6],9,-1069501632),s,d,t[a+11],14,643717713),c,s,t[a],20,-373897302),l=p(l,c=p(c,s=p(s,d,l,c,t[a+5],5,-701558691),d,l,t[a+10],9,38016083),s,d,t[a+15],14,-660478335),c,s,t[a+4],20,-405537848),l=p(l,c=p(c,s=p(s,d,l,c,t[a+9],5,568446438),d,l,t[a+14],9,-1019803690),s,d,t[a+3],14,-187363961),c,s,t[a+8],20,1163531501),l=p(l,c=p(c,s=p(s,d,l,c,t[a+13],5,-1444681467),d,l,t[a+2],9,-51403784),s,d,t[a+7],14,1735328473),c,s,t[a+12],20,-1926607734),l=h(l,c=h(c,s=h(s,d,l,c,t[a+5],4,-378558),d,l,t[a+8],11,-2022574463),s,d,t[a+11],16,1839030562),c,s,t[a+14],23,-35309556),l=h(l,c=h(c,s=h(s,d,l,c,t[a+1],4,-1530992060),d,l,t[a+4],11,1272893353),s,d,t[a+7],16,-155497632),c,s,t[a+10],23,-1094730640),l=h(l,c=h(c,s=h(s,d,l,c,t[a+13],4,681279174),d,l,t[a],11,-358537222),s,d,t[a+3],16,-722521979),c,s,t[a+6],23,76029189),l=h(l,c=h(c,s=h(s,d,l,c,t[a+9],4,-640364487),d,l,t[a+12],11,-421815835),s,d,t[a+15],16,530742520),c,s,t[a+2],23,-995338651),l=y(l,c=y(c,s=y(s,d,l,c,t[a],6,-198630844),d,l,t[a+7],10,1126891415),s,d,t[a+14],15,-1416354905),c,s,t[a+5],21,-57434055),l=y(l,c=y(c,s=y(s,d,l,c,t[a+12],6,1700485571),d,l,t[a+3],10,-1894986606),s,d,t[a+10],15,-1051523),c,s,t[a+1],21,-2054922799),l=y(l,c=y(c,s=y(s,d,l,c,t[a+8],6,1873313359),d,l,t[a+15],10,-30611744),s,d,t[a+6],15,-1560198380),c,s,t[a+13],21,1309151649),l=y(l,c=y(c,s=y(s,d,l,c,t[a+4],6,-145523070),d,l,t[a+11],10,-1120210379),s,d,t[a+2],15,718787259),c,s,t[a+9],21,-343485551),s=u(s,r),d=u(d,n),l=u(l,o),c=u(c,i);return[s,d,l,c]}function l(t){var e,a="",r=32*t.length;for(e=0;e<r;e+=8)a+=String.fromCharCode(t[e>>5]>>>e%32&255);return a}function c(t){var e,a=[];for(a[(t.length>>2)-1]=void 0,e=0;e<a.length;e+=1)a[e]=0;var r=8*t.length;for(e=0;e<r;e+=8)a[e>>5]|=(255&t.charCodeAt(e/8))<<e%32;return a}function r(t){var e,a,r="";for(a=0;a<t.length;a+=1)e=t.charCodeAt(a),r+="0123456789abcdef".charAt(e>>>4&15)+"0123456789abcdef".charAt(15&e);return r}function a(t){return unescape(encodeURIComponent(t))}function n(t){return l(d(c(e=a(t)),8*e.length));var e}function o(t,e){return function(t,e){var a,r,n=c(t),o=[],i=[];for(o[15]=i[15]=void 0,16<n.length&&(n=d(n,8*t.length)),a=0;a<16;a+=1)o[a]=909522486^n[a],i[a]=1549556828^n[a];return r=d(o.concat(c(e)),512+8*e.length),l(d(i.concat(r),640))}(a(t),a(e))}function e(t,e,a){return e?a?o(e,t):r(o(e,t)):a?n(t):r(n(t))}"function"==typeof define&&define.amd?define(function(){return e}):t.md5=e}(o),o.JsEncryptUtil={},o.JsEncryptUtil.md5=o.md5,o.RuleMap={CLLHomeViewController:"homeAd",CLLGrayLineDetailViewController:"homeAd",CLLTransferMainViewController:"homeAd",CLLEnergyMainViewController:"homeAd",CLLMoreMainViewController:"homeAd"}}(global),global.AddModule("event.do",function(t){var k={};return t=t||this,exports={},k.exports=exports,function(t,e,a){var i,n="https://atrace.chelaile.net.cn/thirdSimple",s="https://atrace.chelaile.net.cn/thirdPartyResponse";if("function"==typeof GetConfig){var r=GetConfig();i="dev"==r.server?"https://dev.chelaile.net.cn/adpub/adv!closeAd.action":"stage"==r.server?"https://stage.chelaile.net.cn/adpub/adv!closeAd.action":"https://api.chelaile.net.cn/adpub/adv!closeAd.action"}function d(t,e,a){var r=function(t,e){var a="",r="",n="";if(0<=t.indexOf("#")){var o=t.split("#");a=0<=o[0].indexOf("?")?o[0]+"&":o[0]+"?",r="#"+o.slice(1).join("#")}else a=0<=t.indexOf("?")?t+"&":t+"?";for(var i in e)n+="&"+i+"="+encodeURIComponent(e[i]);return a+n+r}(t,e);null!=a&&null!=a&&"string"!=typeof a&&(a=JSON.stringify(a)),a?Http.post(r,null,a,5e3,function(t,e,a){console.log("sendTrackRequest ret="+t+" response.header="+JSON.stringify(OCValueForKey(e,"allHeaderFields"))+" error="+a)}):Http.get(r,null,5e3,function(t,e,a){console.log("sendTrackRequest ret="+t+" response.header="+JSON.stringify(OCValueForKey(e,"allHeaderFields"))+" error="+a)})}function l(t,e,a){null!=a&&null!=a&&(t[e]=a)}function c(t,e){var a={},r=e.info||{},n=t.traceInfo||{};return l(a,"traceid",n.traceid),l(a,"pid",n.pid),l(a,"aid",t.aid),l(a,"ad_order",r.ad_order),l(a,"adid",r.adid),l(a,"startMode",r.startMode),l(a,"stats_act",r.stats_act),l(a,"viewstatus",e.viewstatus),a}function o(t,e){var a=c(t,e),r=e.info||{};t.traceInfo;if(l(a,"show_status",r.show_status||0),l(a,"cost_time",r.cost_time),l(a,"is_backup",r.is_backup),l(a,"adv_title",r.head),l(a,"adv_desc",r.subhead),d("https://atrace.chelaile.net.cn/exhibit",a),r.unfoldMonitorLink&&r.actionMonitorLink){var n=r.actionMonitorLink.replace("{action}","5"),o=+new Date+"";o=String(o).slice(0,-3),n=(n=n.replace("{time}",o)).replace("{play_time}","0"),d(r.unfoldMonitorLink,{}),d(n,{})}}function u(t,e){var a=c(t,e),r=e.info||{};t.traceInfo;if(l(a,"adv_title",r.head),l(a,"adv_desc",r.subhead),d("https://atrace.chelaile.net.cn/click",a),r.actionMonitorLink){var n=r.actionMonitorLink.replace("{action}","6"),o=+new Date+"";o=String(o).slice(0,-3),d(n=(n=n.replace("{time}",o)).replace("{play_time}","0"),{})}r.clickMonitorLink&&d(r.clickMonitorLink,{})}function f(t,e){d("https://atrace.chelaile.net.cn/close",c(t,e));var a=e.info||{};if(a.isSplash||a.isFloat){if(a.actionMonitorLink){var r=a.actionMonitorLink.replace("{action}","7"),n=+new Date+"";n=String(n).slice(0,-3),d(r=(r=r.replace("{time}",n)).replace("{play_time}","0"),{})}}else!function(t,e){if(i){var a=c(t,e),r=e.info||{},n=(t.traceInfo,r.picsList),o="";n&&n.length&&(o=n.join(";")),l(a,"adv_title",r.head),l(a,"adv_image",o),d(i+"?"+GetDeviceInfo(),a)}}(t,e)}function p(t){var e={},a=(t=t||{}).task||{},r=t.rule||{},n=t.userdata||{};return r.traceInfo&&(l(e,"traceid",r.traceInfo.traceid),l(e,"pid",r.traceInfo.pid)),a.aid&&l(e,"aid",a.aid()),l(e,"req_timestamp",+new Date),l(e,"eventId",n.uniReqId),e}function h(t){var e=p(t),a=((t=t||{}).task,t.rule,t.data||{}),r=function(t){if(t&&t.adEntityArray&&0<t.adEntityArray.length)return t.adEntityArray[0].info}(a),n=r||{};l(e,"ad_order",n.ad_order),l(e,"adid",n.adid),l(e,"req_time",(a.sdk&&a.sdk.didReqTime||0)-(a.sdk&&a.sdk.willReqTime||0)),l(e,"ad_status",r?1:0),l(e,"resp_size",a.contentLength),t.error?l(e,"code",t.error):l(e,"code",OCValueForKey(a.extensionData,"statusCode"));var o={};l(o,"adv_title",n.head),l(o,"adv_desc",n.subhead),l(o,"icon_image",n.pic),l(o,"main_image",n.picsList),l(o,"link",n.link),l(o,"url_type",n.adType),d(s,e,o)}function y(t,e,a){var r;console.log("trackEvent eventId="+t+" eventType="+e+" params="+JSON.stringify(a||{})),e==TrackClass.Type.LoadSplash||e==TrackClass.Type.LoadBanner?(r=p(a),d(n,r)):e==TrackClass.Type.LoadedSplash||e==TrackClass.Type.LoadedBanner?h(a):e!=TrackClass.Type.FailedSplash&&e!=TrackClass.Type.FailedBanner||h(a)}a.trackEvent=y,k.exports={trackExhibit:o,trackClick:u,trackClose:f,trackEvent:y},a.TrackClass={trackExhibit:o,trackClick:u,trackClose:f,trackEvent:y,Type:{LoadSplash:"LoadSplash",LoadBanner:"LoadBanner",LoadedSplash:"LoadedSplash",LoadedBanner:"LoadedBanner",FailedSplash:"FailedSplash",FailedBanner:"FailedBanner",AllAdTimeout:"AllAdTimeout",FetchedAd:"FetchedAd",NoDataLastGroup:"NoDataLastGroup"}}}(0,exports,t),k.exports}),global.AddModule("fetch.do",function(t){var i={};return t=t||this,exports={},i.exports=exports,function(t,e,a){var n="function"==typeof Symbol&&"symbol"===_typeof2(Symbol.iterator)?function(t){return void 0===t?"undefined":_typeof2(t)}:function(t){return t&&"function"==typeof Symbol&&t.constructor===Symbol&&t!==Symbol.prototype?"symbol":void 0===t?"undefined":_typeof2(t)};var r={};function o(t){var a=[];return(t=t||[]).forEach(function(t){var e=function(t){var e=r["sdk"];if(!e){try{e=require("sdks/"+"sdk")}catch(t){console.log(t)}e&&(r.sdk=e)}return e}(t.sdkname());e&&a.push({task:t,sdk:e})}),a}var f=1e3,p=2e3;function h(){return(new Date).getTime()}i.exports=function(e,t,a){if(!e)return a(null);Array.isArray(e.timeouts)&&(f=e.timeouts[0]||f,p=e.timeouts[1]||p);var r=function(t){t&&"object"==(void 0===t?"undefined":n(t))&&t.sdk&&(t.sdk.refreshTime=15e3,t.sdk.traceInfo=e.traceInfo,t.sdk.mixRefreshAdInterval=5e3,t.sdk.maxSplashTimeout=8e3,t.sdk.warmSplashIntervalTime=18e5),a(t)};r.userdata=t,!e.tasks||e.tasks.length<=0?a(null):function e(i,a,s){var r=i.tasks;function d(t){return t?(TrackClass.trackEvent(s.userdata.uniReqId,TrackClass.Type.FetchedAd,{data:t,rule:i,userdata:s.userdata}),s(t)):a==r.length-1?(TrackClass.trackEvent(s.userdata.uniReqId,TrackClass.Type.NoDataLastGroup,{rule:i,userdata:s.userdata}),s(null)):void e(i,a+1,s)}function l(a){n&&clearInterval(n),u.forEach(function(t,e){e!==a&&t.sdk.stop2&&t.sdk.stop2(t.task)})}function t(){var t=h()-c;if(f+p<t)return TrackClass.trackEvent(s.userdata.uniReqId,TrackClass.Type.AllAdTimeout,{used:t,rule:i,userdata:s.userdata}),l(),void d(null);for(var e,a,r=0,n=0,o=0;o<u.length;o++)if(e=u[o],null!=(a=e._result)&&(r++,a[0]&&(n++,f<t||0==o)))return l(o),void d(a[0]);r>=u.length&&(0==n?(console.log("All finish without any succeed."),l(),d(null)):(l(),0<minIndex&&minIndex<u.length-1&&(e=u[minIndex],d((a=e._result)[0]))))}t._count=0;var c=h(),u=o(r[a]);u.forEach(function(e){var t=e.task.adurl_ios();console.log("try sdk: "+t.url),e.sdk.load(e.task,i,s.userdata,f+p,function(t){console.log("uniReqId="+s.userdata.uniReqId+" data comes "+t),e._result=[t]})});var n=setInterval(t,50)}(e,0,r)}}(0,exports),i.exports}),global.AddModule("sdks/sdk.do",function(t){var r={};return t=t||this,exports={},r.exports=exports,function(t,e,a){function o(n,t,o,i){var e,a,r,s,d,l=!1;if(o&&o.adEntityArray&&o.adEntityArray.length&&n.data&&n.data.ad_data&&"AsyncPostData"==n.data.ad_data){var c="";n.data.postData&&(c=JSON.stringify(n.data.postData)),l=!0,o.adEntityArray[0].info=void 0,e=n.data.placementId,a=null,r=c,s=t,d=function(t,e,a){if(n.data.dataFormater&&n.data.dataFormater.parse&&t){var r=n.data.dataFormater.parse(t);r&&r.length?(o.sdk&&(o.sdk.finishedReqTime=+new Date,o.sdk.didReqTime=+new Date),o.adEntityArray[0].info=r[0],i()):i("-90003")}else i(OCValueForKey(a,"code"))},Http.post(e,a,r,s,function(t,e,a){d(t,e,a)})}l||i()}r.exports={load:function(d,l,c,t,u){var e={GDTSDK:"CLLGdtSdk",BaiduSDK:"CLLBaiduSdk",TOUTIAOSDK:"CLLTTSdk",IFLYSDK:"CLLIflySdk",InMobiSdk:"CLLInMobiSdk"},n=d.adurl_ios();n.url&&0==n.url.toLowerCase().indexOf("http")?(n.data=n.data||{},n.data.placementId=n.url,n.url="CLLAdApi"):n.url&&e[n.url]&&(n.url=e[n.url]),n.url;var a=newInstance(n.url);if(!a)return u(null);if(n.type=n.type||n.pos,"splash"==n.type)TrackClass.trackEvent(c.uniReqId,TrackClass.Type.LoadSplash,{userdata:c,rule:l,task:d}),a.loadSplash(n.data,c,t,function(r){o(n,t,r,function(t){if(t)return TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedSplash,{error:t,des:""+t,userdata:c,data:r,rule:l,task:d}),void u(null);try{d.aid&&r.sdk&&(r.sdk.aid=d.aid());var e=d.filter_ios;if(e&&r&&(r.adEntityArray=e(r.adEntityArray)),console.log("1info info = "+a),c&&r.adEntityArray&&0<r.adEntityArray.length){var a=r.adEntityArray[0].info;c.startMode&&(a.startMode=c.startMode),console.log("info info = "+a),a.isSplash=!0,1==a.provider_id||a.link||(a.targetType=1),r.adEntityArray[0].info=a}TrackClass.trackEvent(c.uniReqId,TrackClass.Type.LoadedSplash,{data:r,userdata:c,rule:l,task:d}),u(r)}catch(t){TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedSplash,{error:"-91000",des:""+t,requestInfo:n,userdata:c,rule:l,task:d}),u(null)}})},function(t){t=t||"-90000",TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedSplash,{error:t,requestInfo:n,userdata:c,rule:l,task:d}),u(null)});else if("banner"==n.type){if(TrackClass.trackEvent(c.uniReqId,TrackClass.Type.LoadBanner,{userdata:c,rule:l,task:d}),d.adStyle){var r={1:{showWidth:180,showHeight:88},2:{showWidth:96,showHeight:64},5:{showWidth:96,showHeight:64}}[d.adStyle()+""];r&&(c.showWidth=r.showWidth,c.showHeight=r.showHeight)}a.loadBanner(n.data,c,t,function(s){o(n,t,s,function(t){if(t)return TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedBanner,{error:t,des:""+t,userdata:c,data:s,rule:l,task:d}),void u(null);try{if(d.aid&&s.sdk&&(s.sdk.aid=d.aid()),s&&s.adEntityArray)for(var e=l&&l.closeInfo&&l.closeInfo.closePic,a=0;a<s.adEntityArray.length;a++){var r=s.adEntityArray[a],n=r.info;if(n){if(e&&(n.closePic=e),d.adStyle&&(n.displayType=""==d.adStyle()?2:parseInt(d.adStyle())),n.displayType&&(n.displayType=parseInt(n.displayType)),n.head=n.head||"",n.subhead=n.subhead||"",n.stats_act=c.stats_act,n.head.length>n.subhead.length){var o=n.subhead;n.subhead=n.head,n.head=o}c.startMode&&(n.startMode=c.startMode,n.isSplash=!0,1==n.provider_id||n.link||(n.targetType=1)),r.info=n}}var i=d.filter_ios;i&&s&&(s.adEntityArray=i(s.adEntityArray)),TrackClass.trackEvent(c.uniReqId,TrackClass.Type.LoadedBanner,{userdata:c,data:s,rule:l,task:d}),u(s)}catch(t){TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedBanner,{error:"-91001",des:""+t,userdata:c,data:s,rule:l,task:d}),u(null)}})},function(t,e,a){t=t||"-90001",TrackClass.trackEvent(c.uniReqId,TrackClass.Type.FailedBanner,{error:t,requestInfo:n,userdata:c,data:e,rule:l,task:d}),u(null)})}},stop2:function(t){t.adurl_ios().url}}}(0,exports),r.exports}),require("./event");