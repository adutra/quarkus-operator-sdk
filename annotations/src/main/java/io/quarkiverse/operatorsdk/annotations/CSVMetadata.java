package io.quarkiverse.operatorsdk.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@SuppressWarnings("unused")
public @interface CSVMetadata {
    String name() default "";

    Annotations annotations() default @Annotations;

    String description() default "";

    String displayName() default "";

    Icon[] icon() default {};

    String[] keywords() default "";

    String maturity() default "";

    String version() default "";

    String replaces() default "";

    Maintainer[] maintainers() default {};

    Provider provider() default @Provider;

    InstallMode[] installModes() default {};

    PermissionRule[] permissionRules() default {};

    RequiredCRD[] requiredCRDs() default {};

    String minKubeVersion() default "";

    Link[] links() default {};

    @interface Annotations {
        String containerImage() default "";

        String repository() default "";

        String capabilities() default "";

        String categories() default "";

        boolean certified() default false;

        String almExamples() default "";

        String skipRange() default "";

        Annotation[] others() default {};

        @interface Annotation {
            String name();

            String value();
        }
    }

    @interface Icon {
        String DEFAULT_MEDIA_TYPE = "image/svg+xml";

        String fileName();

        String mediatype() default DEFAULT_MEDIA_TYPE;
    }

    @interface Maintainer {
        String email() default "";

        String name() default "";
    }

    @interface Provider {
        String name() default "";

        String url() default "";
    }

    @interface InstallMode {
        String type();

        boolean supported() default true;
    }

    /**
     * Additional RBAC rules that need to be provided because they cannot be inferred automatically. Note that RBAC rules added
     * to your reconciler via {@link RBACRule} should already be handled automatically, under the service account name
     * associated with your Reconciler so this annotation should only be used to add additional rules to other service accounts
     * or for rules that you don't want to appear in the generated Kubernetes manifests.
     */
    @interface PermissionRule {
        String[] apiGroups();

        String[] resources();

        String[] verbs() default { "get", "list", "watch", "create", "delete", "patch", "update" };

        /**
         * @return the service account name to which the permission rule will be assigned. If not provided, the default service
         *         account name as defined for your operator will be used. Note that for the rule to be effectively added to the
         *         CSV, a service account with that name <em>must</em> exist in the generated kubernetes manifests as this is
         *         the base upon which the bundle generator works. This means that if you add a rule that targets a service
         *         account that is not present in the generated manifest, then this rule won't appear in the generated CSV.
         */
        String serviceAccountName() default "";
    }

    @interface RequiredCRD {
        String kind();

        String name();

        String version();
    }

    @interface Link {
        String url();

        String name();
    }
}
