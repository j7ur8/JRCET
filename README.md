# JRCET
瞎做做，类似WebBook，是个长期项目，虽然WebBook很久没有更新了（= =会更新的）
但是都是打算终身学习的项目。

mvn+jdk8+idea 开发

# 集成内容

分为Setting、Exploit、Tools和Asset模块。

Setting和Exploit尚未完善。

Tools包含以下几个工具
- Intruder模块：有aes、rsa、base、ascii、unicode
- Dencrypt模块：有aes、rsa、base、ascii、unicode加解密
- Rscript模块：一键发送生产python脚本（功能还很简陋，后面慢慢写）
- SOLibrary模块：不会开放的
- HText模块：有几个日常渗透过程中文本处理的小工具，去重、大小写转换、排序、加前后缀、拼音解析等
- Dominate模块：ip反查域名、域名查ip、备案等（有bug所以暂时功能没有开放）
- Password模块：输入姓名拼音全称，生成对应的密码

## Tools模块

暂时不详细介绍（没有按钮执行的一律在文本框中`按住command(ctrl)后按g键并释放即可允许`。

## Asset模块
快速分类资产

# 下一步计划
1. 完善使用说明（需要时间，因为gif更直观）
2. 考虑提供API接口
3. jwt解析
4. 进一步完善各个模块
   - Asset模块是否提供多选