package hbase;

import com.easou.let.utils.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ES-BF-IT-126
 * Date: 17-1-12
 * Time: 下午4:03
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) throws IOException {
        File file= new File("D:\\join0116\\asp.temp.20170116");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        List<String> joinSid = new ArrayList<String>();
        List<String> nojoinSid = new ArrayList<String>();
        String str = "b82551ac-ff04-42ff-9cdc-13a45c364f42 d8b4bbf2-c698-4ffd-bd65-bf1fb82d2c7c cccbedde-3b10-4c2e-b825-128679fbe3d1 47f66dab-5318-4664-80c9-9185f08bb2cd 3ff6e982-ae2c-4861-ad08-0377c12db56b 969cfd26-f119-4043-84d2-9a2005fe7242 969cfd26-f119-4043-84d2-9a2005fe7242 a7fb0e94-aa1a-4866-bbb3-40ea389bc599 54f5b737-3bf8-48d1-83d3-467d04275701 9f8ba729-93da-4b35-86c5-39cadb8f2755 9fea532f-3bac-4544-b165-31a1ac9a252e cf672038-bcbf-4e3e-8ed0-44303cb5051b cf672038-bcbf-4e3e-8ed0-44303cb5051b 2c22a167-6e71-497a-8ef8-1dd8bfb046fb d12334ba-bd25-479d-b9d6-68ea835b6c04 05afb51b-2164-4f37-b385-faa6ce8b3f52 8ac851d1-5a61-4145-b612-8e403e20bca4 921683c9-f570-4252-b0bf-5688e2314711 e01ee637-bbd3-4594-86bf-b7e88cb3b49d 817b0de2-2563-424d-9f72-bbda482d3d45 9555ad28-b152-4497-bd7d-ba6deed40b5e a68be14f-73d7-4167-b797-1c20e09dbbc3 e4c9d760-0821-4a2e-9b8f-3c2bf1463422 078e089a-8308-4fbd-bdf1-2264000705ef 0b4c07b4-b0bf-49af-96e2-1e340a7ed502 dea0554d-8fe0-4663-9446-4631be1075b0 84b4b858-696a-43b9-8e62-6bf827477ee6 a66bb5ad-8d27-4980-a176-647f7aa0e892 7506f494-09f0-444f-a185-4eb45d919d2a cf48babd-1cdd-443b-850a-3d7ce71442bf bc7cac32-122c-47e6-b249-e930193acdf0 d72b0b94-36ff-4acb-bf5c-e3266facf6ce 1d18f075-ca88-4264-b835-f606bc015d19 7fce664a-1776-4fd5-ab46-f8f34e9efe4f e8fda61e-3398-4a2f-867e-851cb5c47b14 f92133cc-eedc-4abd-8514-dd5e1a0fd532 06c240df-332d-4bdd-8aa7-d22193b20ea4 a3a478cd-564d-44c6-9f56-44bda0e03d15 44806835-f1b3-40f6-ab58-6e8372d7b268 820fdfcc-d0df-4571-b2ad-f4d581a8940b 866cbe45-7e1b-47cf-b4d2-76fee95bf880 6511a254-0b52-4a11-8847-86d37b9e88a3 3528370e-83c5-4538-9c8e-4442548e5912 50849cff-2c54-4048-8b56-59ca5acd1b84 7ddffe03-7b22-4de5-9474-8e97560ccd9d 7b5f9bc2-5064-470a-9166-439e797a41ba e72af8f9-0537-4100-89d5-ca02a1935798 947f349b-3e71-491a-8c2e-5e770565fa24 93c2f19f-ca77-4cd9-8036-28b621096439 3293de84-1a03-450e-8a4f-1c00bb0cef9f 813555c7-d42b-4f79-817a-fd9f14ed4c3c 0a4528c0-2def-4e5e-ad94-d4bc18c6f6e5 ef03327b-a519-4c37-ac0b-a3735741f608 67918761-e186-4ede-9792-ae16b6654b4d 17831f53-1aa1-4b82-8b98-0b4e469d2c53 e1df5538-f6e4-4661-8e79-a86d2203d242 fa625329-9e53-4f86-8284-a5d1eb1ace5e 53675632-4432-490b-860d-89280b0cada5 1f430363-477b-4afc-8271-fb9fb3fb0bfe 5b7356df-83d3-4ef7-b0f3-e0ef29b68b60 65a5b9a7-f52f-4157-bfe5-f088014fcca8 3e3b3a51-e698-4664-84be-05504066c1af bb45fd73-e04b-42c7-8d25-cf6dddd434ff 3dd77d76-134d-4690-9ea0-809060e85824 59f30314-3b8d-4058-ba1d-29c747b822e8 c898c492-e817-4d2a-87e1-0535666a09e3 04bd3c9c-4f3e-4445-bdee-2ec396da97e6 b387d2a5-b07e-4a1a-b45a-b7c5a403c43e bcc0ad79-b1c3-4b4c-ab56-0d0583f504e7 d17b761c-ba3c-4bff-ba5b-132195d1ea0f 8fdf201e-c83e-4dad-be3e-53f0cc067c11 a1c41d61-5eb7-4078-bb3a-3d30e4968932 25dd7057-13cd-492a-8011-c36a7f4658c0 b7ff9cf0-839a-417b-85f6-11bc87c779da 2e387892-bb6c-47b1-8e75-f7aba84c02dd 9e64572f-a1e3-4d0a-bd65-e4f7b813d9c1 9c894e02-1fe4-4579-8161-feaa5796f93f a9e2e5e2-fbf9-4bff-bbeb-56e593507d7c 2219d4ad-441c-470f-ab25-c7fd4a3df84e 398e2025-8ae2-4033-8de1-b8ac1956ed57 2623c8d1-f9bc-4629-b915-3e857c3878a9 f1341a1d-0de6-425d-98f5-c9c89a084b12 a15c0154-c02b-4652-9e23-e7727c5d5a39 e1d88e43-786c-4849-b8f4-974f88857b7a 9d43dc5d-c52b-4506-aced-314c0d5e95ab a0f00f6f-f0aa-4fb0-9cfd-326bef16b397 9dd2e578-eb43-4637-ba18-41208a64a4f0 24730cbd-6630-41c6-a071-d2d161b6fb01 f9cc55fb-afcc-4403-8228-6b89b59f8625 f9cc55fb-afcc-4403-8228-6b89b59f8625 6072a6d7-003e-4b3d-b996-54b2deb8ca9a 6072a6d7-003e-4b3d-b996-54b2deb8ca9a 31af7db1-e70e-469d-a40c-6cbc89d436ed 622b2631-c750-444b-8083-3924418b4ddb 6bae7e2c-2e2d-4215-85df-73d6b9534e5b e284e596-795b-4e2b-bc8a-443d9dc7810e b0429fa9-897e-459a-b5ec-8026b7abbf91 e9493c5e-fdc5-4616-baaa-44311f6b2e90 26b20222-e874-4b85-b541-5439390e282b c46e8972-a4a3-4119-8be4-1450178555ce 493a6db5-4535-4dd3-818a-54648b42b93d 5afec272-f084-4fe6-b6cf-3e96cd4066ce 083cf17e-bff1-4b79-a218-42ee3b58e7c5 99908f25-f102-4c06-bca7-8ea42951826a c4f6875f-a2d0-463e-8c07-4f92d18dcb8b e1eee2d4-4b9f-4f14-bb1f-f38210e888d5 4c9d9b47-30c7-4a32-8951-e527d5386aa9 e3b3b343-7513-488c-9a57-18cf50730620 3ed89d63-94bf-415a-baff-0896b44bf1c6 087c4a97-2f07-4331-a108-53cc00db49d8 9a22d014-639a-4359-83b1-5242d8af2326 f73b5bbb-7c43-4b59-96a5-bdd45ce7b549 a00e3be2-2dcc-414b-9abf-d07f0be2859e 791b9855-1d94-4259-a51a-8c36aa46bc0f e6b28456-6593-494a-845d-5d08231894f7 48950ec6-147d-4d0e-bbe2-3d3006b179f5 7c32433f-33a8-456d-a25f-36ab848c482f 6c6428f4-af3c-47df-b024-1c4236197221 c3495f06-d944-4131-8d97-1a0b45641259 7e03b1c6-9a2f-4d1d-b374-8ad8cf062718 7e03b1c6-9a2f-4d1d-b374-8ad8cf062718 f32416d8-c936-400b-b27e-d1b180da65ba a93e28ea-fe7c-46c3-9ef0-bdeab4b9627f 931bc1f6-73a0-4538-900c-345a91bf73f6 ab7e7e22-2feb-47a8-8b67-4848dba5c31a 66dc2619-983e-40c4-99c2-eaebcb4c5789 5d4c7215-3fc7-4fbe-99ee-f071c994041b 4636b0c1-34f7-4064-8e56-4c0de714db56 f7564e8d-1d6a-4fdf-b6f1-7b71e2aaa536 b7fa7c57-3195-4c9a-87bf-fff0154257ae 98aae3b2-1f5d-47ee-9d5e-b95208a6d5c0 9c5a7936-229e-4d33-8b4c-50a635bd2832 492dc12a-d8ff-42d0-94d1-a88ab6b227a9 ab1e91d5-5303-4f69-adfe-ef5600741906 8da0da45-9184-4a8e-9ca2-e102c3bda2a4 9579b32e-cf9e-4ea5-9ba8-b502af146f44 21c92ecd-b494-4303-b6ed-9c8c59679eec 92d40b07-5559-4552-85d0-3c9917b30645 76e98642-bbf2-440f-bfb1-c6dd1fc0bc00 7f96daa0-2e83-46db-bcdb-6ccb290dc3c5 fa12cbc1-234d-4843-a99c-c2124a79166d c6dd2037-8614-47ca-a89f-9189a835ea3f b0c66f41-25e4-4622-ba01-23d601d5f556 8718c731-87d2-4fcd-96a7-5481d4092544 8072781e-3a0d-43fa-b2e6-3c0156587da5 fde7f61e-50ad-4993-aa84-490f5eac4bc8 5c4af0f5-bb96-400c-8bec-5c8e2b187169 dbac58c4-f4af-4816-8afa-ac12ca73667b 60149869-bb10-45ef-8016-a2df4e145bf1 73f416b5-7a51-439f-be38-f0cd2dc1e2b7 93f097dc-818f-4825-879d-fe1b0850492c c754a0b5-1ac7-4744-b421-d858ec5c9a6e 2d4ddbb1-4023-43ad-baf6-6959d2850940 9c151fc3-2319-421f-9c62-cadbe9f916a6 dd89616e-874f-404a-8713-4f55e30e1645 dd589c5a-48c6-46a0-b5e1-999b0357e7ca c0848533-b827-4ae9-b5a0-dc3e1914428d 782e6aba-f571-40b6-b59e-35ebccccb3f1 782e6aba-f571-40b6-b59e-35ebccccb3f1 3fa8b370-7749-453b-9a9c-dc6f52cd6b26 64289b5a-ad12-43fa-8718-a92d8eef644e 0e342b93-237e-4f8f-8a5f-f86249e1f303 79cd0df7-2454-4cc0-a031-4abf2cd25836 fdf3827d-ab5f-4815-8907-0f4117b69fbc 355df426-8edd-43fb-9e82-9a60ea40cea8 e5f37931-2ef3-4a32-af4e-8fc5b7040eda 60679969-592a-44d7-9201-58081c6eeee8 842306be-81ff-4942-a70c-ed4829a58e68 772df744-bae5-4a05-ae6e-67d9c36e83ac 15b16391-a5fa-4e8d-919d-7706b72eecba 3c1aef15-56b8-4e99-96cf-e37f3aac0f52 2584eeb0-5c5d-4268-8540-495f828c67cb 4b71b97f-9108-4e6e-9549-f13c63cf3576 b7dc18e1-b694-4b2e-970d-6ef4e91767c5 bd3e4736-d323-4e61-ae5d-f32eccd639c3 0e07d62a-49d2-484a-9294-14a6ea1562ec f236f2c2-c23f-46bd-94aa-b8a9f658eb6c 18bbc8ce-af52-4eb3-a96c-57c9510d285c 2a76f653-45cc-4702-b473-a0483bfcb912 8009ce1b-fe6d-4bae-99bd-df729dc43d06 603896dd-94ec-465a-9be0-763e4cc1eb2b c6bcca87-e414-4077-af82-22d0fa64ebc3 9c200b57-698d-4f56-b424-5ffa3bb0c7ef a357ffd4-0bd3-4f5e-9100-c912d42d37ee d13dfb8f-cec3-46ae-b99f-598e6c9f2359 2926615c-4c6f-4b7a-b127-63329a1ca19c 2eb07a3f-2f06-42d1-93bf-5a0390ff948b 6ef54f04-0e7b-412e-94b5-fe643969faa1 6d5cc091-d2b7-40f9-a96a-a2ff5760eab3 45e46722-c31b-4afe-8bdc-d14878d676d1 fd1b6ff8-4953-43f0-94df-8ad0f90c22a7 54c6ef0a-00bc-4ac5-84ad-7a6ad36664c4 22ce9607-0d2f-49e8-acc5-7c3991c0edc8 abb5745b-8fa1-48cf-9022-dde76934569a 0636a3d7-db8d-45fd-9d81-02694e2cfe58 618c87d0-b0c4-4d67-bffb-d4eac6957f3e 67af63ac-bb80-4ffa-80e8-0ec0f11f3d86 6d961db6-3882-4c08-b6e9-745ab0b16da9 1df0fc64-8991-4e0b-b8cb-482f4d40e03c 840dd247-3ddd-4b16-96ca-afe3e9185a46 08f6876a-d294-4f90-bfdf-0b8d475abf7c 289e616b-b9fb-4be3-81d4-fdc0019a4b14 e4642f23-1944-43bb-8b26-3946d149b35d ce76801c-c501-41c0-847e-edada42f168e 582c8f2f-a6a9-4f4f-b9d1-ec16fdea3402 f93441fb-134c-46d7-86ee-073d728fe61d 9ae4ce30-ab16-4e38-b77f-fdd8c2dc6d91 2f43b482-36ae-4f2a-9152-bc36cdf5f01e";
        String[] strArray = str.split(" ");
        while ((line = bufferedReader.readLine()) != null){
            for(String sid : strArray){
                if(line.contains(sid)){
                    joinSid.add(sid);
                    //System.out.println(sid);
                }
            }
        }

        for(String sid : strArray){
            boolean flag = false;
            for(String joinstr : joinSid){
                if(sid.equals(joinstr)){
                    flag = true;
                }
            }
            if(!flag){
                nojoinSid.add(sid);
            }
        }

        File cdpfile= new File("D:\\join0116\\click.temp.20170116");
        FileReader cdpfileReader = new FileReader(cdpfile);
        BufferedReader cdpbufferedReader = new BufferedReader(cdpfileReader);
        while ((line = cdpbufferedReader.readLine()) != null){
            boolean flag = false;
            for(String sid : joinSid){
                if(line.contains(sid)){
                    flag = true;
                }
            }
            if(!flag){
                FileUtils.witeFile(line, "D:\\join0116\\click.temp.20170116.nojoin");
                System.out.println(line);
            }
        }
    }
}
