package command

import (
	"github.com/TypingHare/web.carton/v2026/web/web/share"
	"github.com/spf13/cobra"
)

func ListCommand(d share.WebDecorationLike) *cobra.Command {
	command := &cobra.Command{
		Use:   "list",
		Short: "Show all web records",
		RunE: func(cmd *cobra.Command, args []string) error {
			cabinet, err := share.GetWebCabinet(d.Chamber())
			if err != nil {
				cmd.PrintErrf("failed to get web cabinet: %v\n", err)
				return nil
			}

			for _, webRecord := range cabinet.Objects {
				cmd.Printf("%s  %s", webRecord.Name, webRecord.Url)
			}

			return nil
		},
	}

	return command
}
