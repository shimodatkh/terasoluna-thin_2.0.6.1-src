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

package jp.terasoluna.fw.service.thin;


/**
 * 
 * �r�W�l�X���W�b�N�����N���X���������ׂ��C���^�t�F�[�X�B
 *
 * <p>
 * �r�W�l�X���W�b�N�́A���̃C���^�t�F�[�X���������邱�Ƃɂ��쐬����B
 * �r�W�l�X���W�b�N�̃G���g���|�C���g�ƂȂ�execute()���\�b�h�ɂ́A
 * JavaBean���n�����B<br>
 * ���L�́A�T�^�I�ȃr�W�l�X���W�b�N�̎�����i���O�C�������j�ł���B
 * </p>
 * <h5>������</h5>
 * <code><pre>
 * �E�E�E
 * public BLogicResult execute(LogonBean params) {
 *
 *     BLogicResult result = new BLogicResult();
 *
 *     // ���[�UID
 *     String userId = params.getUserId();
 *     // �p�X���[�h
 *     String password = params.getPassword();
 *     // ���O�C�����s��
 *     String failures = params.getFailures();
 *
 *     int failCount = Integer.parseInt(failures);
 *
 *     // ���O�C�����s�񐔂��K��񐔈ȏ�̎��A�������Ŏ��s
 *     if (failCount &gt; MAX_COUNT ) {
 *         result.setResultString("loginError");
 *         return result;
 *     }
 *
 *     // ���[�U��DB�ɑ��݂��邩
 *     if (isAuthenticated(userId, password)) {
 *         // �o�͒l�Ƃ���JavaBean�𐶐�
 *         LogonResultBean bean = new LogonResultBean();
 *         // ���݂���ꍇ�AUVO�𐶐����A���ʃI�u�W�F�N�g�Ɋi�[
 *         sampleUVO uvo = UserValueObject.createUserValueObject();
 *         bean.setUvo(uvo);
 *         result.setResultObject(bean);
 *         // �F�ؐ����Ƃ��đJ��
 *         result.setResultString("success");
 *     } else {
 *         BLogicMessages errors = new BLogicMessages();
 *         BLogicMessage error = new BLogicMessage("message.error.login");
 *         
 *         errors.add(ActionMessages.GLOBAL_MESSAGE, error);
 *         result.setErrors(errors);
 *         �E�E�E
 *         // �F�؎��s�Ƃ��đJ��
 *         result.setResultString("failure");
 *     }
 *     return result;
 * }
 * </pre></code>
 * <p>
 * ���L�́A�r�W�l�X���W�b�N��������DAO�����s�����ł���B
 * DAO�͗\�ߐݒ肳��Ă�����̂Ƃ���BDAO�̐ݒ�ɂ��ẮA
 * �eDAO�����N���X���Q�Ƃ̂��ƁB
 * </p>
 * <h5>�r�W�l�X���W�b�N�����DAO���s��</h5>
 * <code><pre>
 * private boolean isAuthenticated(String id, String pass) {
 *
 *     // DAO�ɓn���p�����[�^�̍쐬
 *     Map&lt;String, String&gt; parameters = new HashMap&lt;String, String&gt;();
 *     parameters.put("ID", id);
 *     parameters.put("PASSWORD", pass);
 *
 *     // DAO�̎��s
 *     Map&lt;String, Object&gt;[] result = dao.executeForMapList(
 *                                             USER_AUTHENTICATION, parameters);
 *     if (result != null) {
 *        // ���[�U������
 *        return true;
 *     }
 *     // ���[�U�����݂��Ȃ�
 *     return false;
 * }
 * </pre></code>
 * <p>
 *  DB�̃R�~�b�g�A���[���o�b�N�͐錾�I�g�����U�N�V������p���čs�����߁A
 *  �����҂͒���DB�R�l�N�V�����ɑ΂��āA�R�~�b�g�E���[���o�b�N�͍s��Ȃ��B<br>
 *
 *  �֘A����@�\�ɂ��ẮA���L���Q�Ƃ̂��ƁB
 *  <ul>
 *  <li>�o�͏��̏ڍׁFBLogicResult</li>
 *  <li>Web�w�̃I�u�W�F�N�g�ƁA�r�W�l�X���W�b�N�Ƃ̃}�b�s���O�ݒ�ɂ��āF
 *      BLogicIOPlugIn</li>
 *  </ul>
 * </p>
 *
 * @see jp.terasoluna.fw.web.struts.plugins.BLogicIOPlugIn
 * @see jp.terasoluna.fw.service.thin.AbstractBLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicMapper
 * @see jp.terasoluna.fw.service.thin.BLogicIO
 * @see jp.terasoluna.fw.service.thin.BLogicProperty
 * @see jp.terasoluna.fw.service.thin.BLogicResources
 * @see jp.terasoluna.fw.service.thin.BLogicResult
 * @see jp.terasoluna.fw.web.struts.actions.AbstractBLogicAction
 * @see jp.terasoluna.fw.web.struts.actions.BLogicAction
 *
 * @param <P> �r�W�l�X���W�b�N�ւ̓��͒l�ƂȂ�JavaBean���w�肷��
 *
 */
public interface BLogic<P> {

    /**
     * �r�W�l�X���W�b�N�����s����B
     *
     * @param params �r�W�l�X���W�b�N���͏��
     * @return �r�W�l�X���W�b�N��������
     */
    BLogicResult execute(P params);

}
