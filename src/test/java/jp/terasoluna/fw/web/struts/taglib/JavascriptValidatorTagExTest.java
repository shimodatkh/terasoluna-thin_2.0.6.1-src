/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.fw.web.struts.taglib;

import java.io.BufferedReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

import jp.terasoluna.utlib.TagUTUtil;
import jp.terasoluna.utlib.UTUtil;
import jp.terasoluna.utlib.exception.Exception_JspWriterImpl;
import junit.framework.TestCase;

import com.mockrunner.mock.web.MockPageContext;

/**
 * {@link jp.terasoluna.fw.web.struts.taglib.JavascriptValidatorTagEx}
 * �N���X�̃u���b�N�{�b�N�X�e�X�g�B
 * 
 * <p>
 * <h4>�y�N���X�̊T�v�z</h4>
 * Struts���񋟂���AJavascriptValidatorTag�N���X���g�������N���X�̃e�X�g�B<br>
 * <br>
 * doStartTag()�ŁArenderKanaList()�̃e�X�g�P�[�X����܂���B
 * <p>
 * 
 * @see jp.terasoluna.fw.web.struts.taglib.JavascriptValidatorTagEx
 */
public class JavascriptValidatorTagExTest extends TestCase {

    /**
     * �e�X�g�ΏۃN���X
     */
    private JavascriptValidatorTagEx tag = null;

    /**
     * ���̃e�X�g�P�[�X�����s����ׂ�
     * GUI �A�v���P�[�V�������N������B
     * 
     * @param args java �R�}���h�ɐݒ肳�ꂽ�p�����[�^
     */
    public static void main(String[] args) {
        junit.swingui.TestRunner.run(JavascriptValidatorTagExTest.class);
    }

    /**
     * �������������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        tag = (JavascriptValidatorTagEx) TagUTUtil.create(
                JavascriptValidatorTagEx_JavascriptValidatorTagExStub01.class);
    }

    /**
     * �I���������s���B
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * �R���X�g���N�^�B
     * 
     * @param name ���̃e�X�g�P�[�X�̖��O�B
     */
    public JavascriptValidatorTagExTest(String name) {
        super(name);
    }

    /**
     * testDoStartTag01()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) JavascriptValidatorTag#renderJavascript()�̖߂�l:"abc"<br>
     *                <br>
     *                JavascriptValidatorTag#renderJavascript()��
     *                �I�[�o�[���C�h���āA"abc"��ԋp����X�^�u���쐬����B<br>
     *                renderJavascript()�́Asuper.doStartTag()����
     *                �Ăяo����Ă��邽�߁Asuper.doStartTag()��
     *                �Ăяo���m�F�Ƃ��Ďg�p����B<br>
     *         (���) htmlComment<br>
     *                JavascriptValidatorTag�̃N���X�ϐ�:true<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_TAG<br>
     *         (��ԕω�) Writer<br>
     *                    renderKanaList()�̌Ăяo���m�F����сA
     *                    super.doStartTag()�̌Ăяo���m�F���s���B:
     *                    <script type="text/javascript"
     *                     language="Javascript1.1"> <br>
     *                    <br>
     *                    <!-- Begin <br>
     *                    var hankakuKanaList = "����������������������¯�
     *                    ������������������֬�������ܦ��ް�����";<br>
     *                    var zenkakuKanaList = "�A�C�E���G�I�@�B�D�F�H�J
     *                    �L�N�P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c
     *                    �e�g�_�a�d�f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y
     *                    �|�}�~���������������������������������������b���[";<br>
     *                    <br>
     *                    //End --> <br>
     *                    </script><br>
     *                    abc<br>
     *         
     * <br>
     * htmlComment�̒l��"true"�ŁArenderKanaList()�̕ԋp�l�𐳏�ɏo�͂��A
     * super.doStartTag()�𐳏�ɌĂяo�����ꍇ<br>
     * <br>
     * renderKanaList()�̃e�X�g����܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoStartTag01() throws Exception {
        // �O����
        tag.setHtmlComment("true");

        // �e�X�g���{
        int result = tag.doStartTag();

        // ����
        assertEquals(result, BodyTag.EVAL_BODY_TAG);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<script type=\"text/javascript\" " +
                "language=\"Javascript1.1\"> ", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("<!-- Begin ", reader.readLine());
        assertEquals("var hankakuKanaList = \"����������������������¯�����" +
                "��������������֬�������ܦ��ް�����\";", reader.readLine());
        assertEquals("var zenkakuKanaList = \"�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R" +
                "�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l" +
                "�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~��������������������������" +
                "�������������b���[\";", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("//End --> ", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("abc", reader.readLine());
    }

    /**
     * testDoStartTag02()
     * <br><br>
     * 
     * (����n)
     * <br>
     * �ϓ_�FC
     * <br><br>
     * ���͒l�F(���) JavascriptValidatorTag#renderJavascript()�̖߂�l:"abc"<br>
     *                <br>
     *                JavascriptValidatorTag#renderJavascript()��
     *                �I�[�o�[���C�h���āA"abc"��ԋp����X�^�u���쐬����B<br>
     *                renderJavascript()�́Asuper.doStartTag()����
     *                �Ăяo����Ă��邽�߁Asuper.doStartTag()��
     *                �Ăяo���m�F�Ƃ��Ďg�p����B<br>
     *         (���) htmlComment<br>
     *                JavascriptValidatorTag�̃N���X�ϐ�:false<br>
     *         
     * <br>
     * ���Ғl�F(�߂�l) int:EVAL_BODY_TAG<br>
     *         (��ԕω�) Writer<br>
     *                    renderKanaList()�̌Ăяo���m�F����сA
     *                    super.doStartTag()�̌Ăяo���m�F���s���B:
     *                    <script type="text/javascript"
     *                     language="Javascript1.1"> <br>
     *                    var hankakuKanaList = "�����������������������
     *                    ��������������������֬�������ܦ��ް�����";<br>
     *                    var zenkakuKanaList = "�A�C�E���G�I�@�B�D�F�H�J�L�N
     *                    �P�R�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d
     *                    �f�h�i�j�k�l�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~������
     *                    ���������������������������������b���[";<br>
     *                    <br>
     *                    </script><br>
     *                    abc<br>
     *         
     * <br>
     * htmlComment�̒l��"false"�ŁArenderKanaList()�̕ԋp�l�𐳏�ɏo�͂��A
     * super.doStartTag()�𐳏�ɌĂяo�����ꍇ<br>
     * <br>
     * renderKanaList()�̃e�X�g����܂���B
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    @SuppressWarnings("deprecation")
    public void testDoStartTag02() throws Exception {
        // �O����
        tag.setHtmlComment("false");

        // �e�X�g���{
        int result = tag.doStartTag();

        // ����
        assertEquals(result, BodyTag.EVAL_BODY_TAG);
        BufferedReader reader = TagUTUtil.getOutputReader(tag);
        assertEquals("<script type=\"text/javascript\" " +
                "language=\"Javascript1.1\"> ", reader.readLine());
        assertEquals("var hankakuKanaList = \"����������������������¯�����" +
                "��������������֬�������ܦ��ް�����\";", reader.readLine());
        assertEquals("var zenkakuKanaList = \"�A�C�E���G�I�@�B�D�F�H�J�L�N�P�R" +
                "�����K�M�O�Q�S�T�V�X�Z�\�U�W�Y�[�]�^�`�c�e�g�_�a�d�f�h�i�j�k�l" +
                "�m�n�q�t�w�z�o�r�u�x�{�p�s�v�y�|�}�~��������������������������" +
                "�������������b���[\";", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("</script>", reader.readLine());
        assertEquals("", reader.readLine());
        assertEquals("abc", reader.readLine());
    }

    /**
     * testDoStartTag03()
     * <br><br>
     * 
     * (�ُ�n)
     * <br>
     * �ϓ_�FG
     * <br><br>
     * ���͒l�F(���) JspWriter#print():IOException������<br>
     *         
     * <br>
     * ���Ғl�F(��ԕω�) ��O:�N���X�FJspException<br>
     *                    ���b�Z�[�W�F-<br>
     *                    ���b�v������O�F-<br>
     *         
     * <br>
     * JspWriter��IOException�����������ꍇ
     * <br>
     * 
     * @throws Exception ���̃��\�b�h�Ŕ���������O
     */
    public void testDoStartTag03() throws Exception {
        // �O����
        // Mock�I�u�W�F�N�g�̐ݒ�
        MockPageContext pageContext
            = (MockPageContext) TagUTUtil.getPageContext(tag);

        // �e�X�g�pJspWriter�̐���
        Exception_JspWriterImpl out =
            new Exception_JspWriterImpl();
        out.setTrue();

        // �����E�ݒ肵���e�X�g�pJspWriter��PageContext�ɃZ�b�g
        UTUtil.setPrivateField(pageContext, "jspWriter", out);

        // �e�X�g���{
        try{
            tag.doStartTag();
            fail();
        } catch (JspException e) {
            // ���b�Z�[�W���Ȃ����߁AJspException�������ۂ𔻒�
        }
    }

}
