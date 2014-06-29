<?php
    $USER = 'USER_NAME';
    $PASS = 'PASSWORD';
    
    if (!isset($_SERVER['PHP_AUTH_USER'])){
        header("WWW-Authenticate: Basic realm=\"Please Enter Your Password\"");
        header("HTTP/1.0 401 Unauthorized");
        echo "Authorization Required";
        exit;
    }else{
        if ($_SERVER["PHP_AUTH_USER"] === $USER && $_SERVER["PHP_AUTH_PW"] === $PASS){
            
        }else{
                    header("WWW-Authenticate: Basic realm=\"Please Enter Your Password\"");
        header("HTTP/1.0 401 Unauthorized");
        echo "Authorization Required";
        exit;
        }
    }
    
    if($_SERVER['REQUEST_METHOD'] == "POST"){
        if (isset($_FILES['upfile']['error']) && is_int($_FILES['upfile']['error'])){
            try{
                switch ($_FILES['upfile']['error']){
                    case UPLOAD_ERR_OK:
                        break;
                    case UPLOAD_ERR_NO_FILE:
                        throw new RuntimeException('ファイルが選択されていません');
                    case UPLOAD_ERR_INI_SIZE:
                    case UPLOAD_ERR_FORM_SIZE:
                        throw new RuntimeException('サイズが大きすぎます');
                    default:
                        throw new RuntimeException('エラーが発生しました');
                }
                
                $type = @exif_imagetype($_FILES['upfile']['tmp_name']);
                
                if (!in_array($type, array(IMAGETYPE_GIF, IMAGETYPE_JPEG, IMAGETYPE_PNG), true)){
                    throw new RuntimeException('非対応形式です');
                }
                
                $hash = sha1_file($_FILES['upfile']['tmp_name']);
                
                if (!move_uploaded_file(
                        $_FILES['upfile']['tmp_name'], $path = sprintf('./img/%s%s',
                        $hash, image_type_to_extension($type)))){
                    throw new RuntimeException('move error');
                }
                
                chmod($path, 0644);
                
                echo 'http://'.$_SERVER['SERVER_NAME'].'/img/'.$hash.image_type_to_extension($type);
                
            }catch (RuntimeException $e){
                #echo $e;
            }
        }
    }

